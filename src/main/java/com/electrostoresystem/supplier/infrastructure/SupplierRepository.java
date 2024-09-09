package com.electrostoresystem.supplier.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;

public class SupplierRepository implements SupplierService {
    private Connection connection;
    
    public  SupplierRepository(){
         try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("configdb.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSupplier(Supplier supplier) {
        try {
            String query = "INSERT INTO suppliers (id, name, email, city_id, address_details) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, supplier.getId());
            ps.setString(2, supplier.getName());
            ps.setString(3, supplier.getEmail());
            ps.setInt(4, supplier.getCityId());
            ps.setString(5, supplier.getAddressDetails());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("supplier added successfully!");
            } else {
                System.out.println("supplier addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        String query = "UPDATE suppliers SET name = ?, email = ?,city_id = ?, address_details = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getEmail());
            ps.setInt(3, supplier.getCityId());
            ps.setString(4, supplier.getAddressDetails());
            ps.setString(5, supplier.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("supplier updated successfully!");
                JOptionPane.showMessageDialog(null,"Supplier updated successfully");
            }else {
                System.out.println("supplier update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier deleteSupplier(String id) {
        Supplier supplier = null;
        String selectQuery = "SELECT * FROM suppliers WHERE id = ?";
        String deleteQuery = "DELETE FROM suppliers WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setString(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    supplier = new Supplier(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("city_id"),
                        rs.getString("address_details")
                    );
                }
            }
            
            // If customer exists, delete it
            if (supplier != null) {
                deletePs.setString(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Supplier deleted successfully!");
                    return supplier;
                }
            }
            
            System.out.println("Supplier deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Supplier> findSupplierById(String id) {
        String query = "SELECT id, name, email, city_id, address_details FROM suppliers WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Supplier supplier = new Supplier(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("city_id"),
                        rs.getString("address_details")
                    );
                    return Optional.of(supplier);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Supplier> findAllSupplier() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT id, name, email, city_id, address_details FROM suppliers";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Supplier supplier = new Supplier(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("city_id"),
                    rs.getString("address_details")
                );
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return suppliers;
    }

}

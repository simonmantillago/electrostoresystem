package com.electrostoresystem.supplierphone.infrastructure;

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

import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;

public class SupplierPhoneRepository implements SupplierPhoneService {
private Connection connection;

    public SupplierPhoneRepository() {
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
    public void createSupplierPhone(SupplierPhone supplierPhone) {
       try {
            String query = "INSERT INTO supplier_phones (phone,supplier_id) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, supplierPhone.getPhone());
            ps.setString(2, supplierPhone.getSupplierId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("supplierPhone added successfully!");
                JOptionPane.showMessageDialog(null, "SupplierPhone added successfully!");
            } else {
                System.out.println("supplierPhone addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error, Wrong Id or supplier not Registered!");
        }
    }

    @Override
    public void updateSupplierPhone(SupplierPhone supplierPhone,String originalPhone) {
        String query = "UPDATE supplier_phones SET phone = ?, supplier_id = ? WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, supplierPhone.getPhone());
            ps.setString(2, supplierPhone.getSupplierId());
            ps.setString(3, originalPhone);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {     
                System.out.println("SupplierPhone updated successfully!");
                JOptionPane.showMessageDialog(null, "SupplierPhone updated successfully!");
            } else {  
                System.out.println("SupplierPhone update failed!");
                JOptionPane.showMessageDialog(null, "SupplierPhone update failed!");

                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "SupplierPhone update failed!");
            
        }
    }

    @Override
    public SupplierPhone deleteSupplierPhone(String Phone) {
        SupplierPhone supplierPhone = null;
        String selectQuery = "SELECT * FROM supplier_phones WHERE phone = ?";
        String deleteQuery = "DELETE FROM supplier_phones WHERE phone = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
             PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {

            // First, fetch the supplierPhone
            selectPs.setString(1, Phone);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    supplierPhone = new SupplierPhone(
                        rs.getString("supplier_id"),
                        rs.getNString("phone")
                        );
                }
            }

            // If supplierPhone exists, delete it
            if (supplierPhone != null) {
                deletePs.setString(1, Phone);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("SupplierPhone deleted successfully!");
                    return supplierPhone;
                }
            }

            System.out.println("SupplierPhone deletion failed. SupplierPhone not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<SupplierPhone> findAllSupplierPhone() {
        List<SupplierPhone> supplierPhones = new ArrayList<>();
        String query = "SELECT phone,supplier_id FROM supplier_phones";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                SupplierPhone supplierPhone = new SupplierPhone(
                    rs.getString("phone"),
                    rs.getString("supplier_id")
                );
                supplierPhones.add(supplierPhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return supplierPhones;
    }

    @Override
    public List<SupplierPhone> findSupplierPhonesBySupplierId(String supplierId) {
        List<SupplierPhone> supplierPhones = new ArrayList<>();
        String query = "SELECT phone, supplier_id FROM supplier_phones WHERE supplier_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, supplierId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SupplierPhone supplierPhone = new SupplierPhone(
                        rs.getString("supplier_id"),
                        rs.getString("phone")
                    );
                    supplierPhones.add(supplierPhone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return supplierPhones;
    }

    @Override
    public Optional<SupplierPhone> findSupplierPhoneByPhone(String Phone) {
            String query = "SELECT phone,supplier_id FROM supplier_phones WHERE phone = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, Phone);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        SupplierPhone supplierPhone = new SupplierPhone(
                            rs.getString("supplier_id"),
                            rs.getString("phone")
                        );
                        return Optional.of(supplierPhone);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }
}

package com.electrostoresystem.sale.infrastructure;

import java.util.List;
import java.util.Optional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;

public class SaleRepository implements SaleService {
    private Connection connection;
    
    public  SaleRepository(){
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
    public void createSale(Sale sale) {
        try {
            String query = "INSERT INTO sales (client_id,payment_method,discount_percent,status_id) VALUES (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, sale.getClientId());
            ps.setInt(2, sale.getPayMethod());
            ps.setFloat(3, sale.getDiscountPercent());
            ps.setInt(4, sale.getStatusId());
        
            

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("sale added successfully!");
            } else {
                System.out.println("sale addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSale(Sale sale) {
        String query = "UPDATE sales SET client_id = ?, total = ?, payment_method = ?,discount_percent = ?,status_id = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, sale.getClientId());
            ps.setFloat(2, sale.getTotal());
            ps.setInt(3, sale.getPayMethod()); 
            ps.setFloat(4, sale.getDiscountPercent());
            ps.setInt(5, sale.getStatusId());
            ps.setInt(6, sale.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("sale updated successfully!");
                JOptionPane.showMessageDialog(null,"Sale updated successfully");
            }else {
                System.out.println("sale update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sale deleteSale(int id) {
        Sale sale = null;
        String selectQuery = "SELECT * FROM sales WHERE id = ?";
        String deleteQuery = "DELETE FROM sales WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    sale = new Sale(
                        rs.getInt("id"),
                        rs.getString("sale_date"),
                        rs.getString("client_id"),
                        rs.getFloat("total"),
                        rs.getInt("payment_method"),
                        rs.getFloat("discount_amount"),
                        rs.getFloat("discount_percent"),
                        rs.getInt("status_id")
    
                    );
                }
            }
            
            // If customer exists, delete it
            if (sale != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Sale deleted successfully!");
                    return sale;
                }
            }
            
            System.out.println("Sale deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Sale> findSaleById(int id) {
       String query = "SELECT id,sale_date,client_id,total,payment_method,discount_amount,discount_percent,status_id FROM sales WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Sale sale = new Sale(
                        rs.getInt("id"),
                        rs.getString("sale_date"),
                        rs.getString("client_id"),
                        rs.getFloat("total"),
                        rs.getInt("payment_method"),
                        rs.getFloat("discount_amount"),
                        rs.getFloat("discount_percent"),
                        rs.getInt("status_id")
                    );
                    return Optional.of(sale);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Sale> findAllSale() {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT id,sale_date,client_id,total,payment_method,discount_amount,discount_percent,status_id FROM sales";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Sale sale = new Sale(
                    rs.getInt("id"),
                    rs.getString("sale_date"),
                    rs.getString("client_id"),
                    rs.getFloat("total"),
                    rs.getInt("payment_method"),
                    rs.getFloat("discount_amount"),
                    rs.getFloat("discount_percent"),
                    rs.getInt("status_id")
                );
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return sales;
    }

}

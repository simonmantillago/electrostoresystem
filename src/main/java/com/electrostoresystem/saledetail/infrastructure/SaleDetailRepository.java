package com.electrostoresystem.saledetail.infrastructure;

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

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class SaleDetailRepository implements SaleDetailService {
    private Connection connection;
    
    public  SaleDetailRepository(){
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
    public void createSaleDetail(SaleDetail saleDetail) {
        try {
            String query = "INSERT INTO sales_details (sale_id, product_id, quantity) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, saleDetail.getSaleId());
            ps.setInt(2, saleDetail.getProductId());
            ps.setInt(3, saleDetail.getQuantity());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("saleDetail added successfully!");
            } else {
                System.out.println("saleDetail addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Not enough stock, or Product not Registered" );
        }
    }

    @Override
    public void updateSaleDetail(SaleDetail saleDetail) {
        String query = "UPDATE sales_details SET quantity = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, saleDetail.getQuantity());
            ps.setInt(2, saleDetail.getId());
    
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("saleDetail updated successfully!");
                JOptionPane.showMessageDialog(null,"SaleDetail updated successfully");
            }else {
                System.out.println("saleDetail update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SaleDetail deleteSaleDetail(int id) {
        SaleDetail saleDetail = null;
        String selectQuery = "SELECT * FROM sales_details WHERE id = ?";
        String deleteQuery = "DELETE FROM sales_details WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    saleDetail = new SaleDetail(
                        rs.getInt("id"),
                        rs.getInt("sale_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("subtotal")
                    );
                }
            }
            
            // If customer exists, delete it
            if (saleDetail != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("SaleDetail deleted successfully!");
                    return saleDetail;
                }
            }
            
            System.out.println("SaleDetail deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<SaleDetail> findSaleDetailById(int id) {
        String query = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sales_details WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    SaleDetail saleDetail = new SaleDetail(
                        rs.getInt("id"),
                        rs.getInt("sale_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("subtotal")
                    );
                    return Optional.of(saleDetail);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<SaleDetail> findAllSaleDetail() {
        List<SaleDetail> saleDetails = new ArrayList<>();
        String query = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sales_details";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                SaleDetail saleDetail = new SaleDetail(
                    rs.getInt("id"),
                    rs.getInt("sale_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getFloat("unit_price"),
                    rs.getFloat("subtotal")
                );
                saleDetails.add(saleDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return saleDetails;
    }

    @Override
    public List<SaleDetail> findSaleDetailsBySaleId(int saleId) {
        List<SaleDetail> saleDetails = new ArrayList<>();
        String query = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sales_details WHERE sale_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, saleId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SaleDetail saleDetail = new SaleDetail(
                        rs.getInt("id"),
                        rs.getInt("sale_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("subtotal")
                    );
                    saleDetails.add(saleDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return saleDetails;
    }

    @Override
    public void deleteSaleDetailsBySaleId(int saleId) {
        String deleteQuery = "DELETE FROM sales_details WHERE sale_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
            ps.setInt(1, saleId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SaleDetails deleted successfully!");
            } else {
                System.out.println("No SaleDetails found for the given client_id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

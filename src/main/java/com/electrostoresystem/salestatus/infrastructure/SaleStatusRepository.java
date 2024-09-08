package com.electrostoresystem.salestatus.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;

public class SaleStatusRepository implements SaleStatusService{
    private Connection connection;
    
    public  SaleStatusRepository(){
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
    public void createSaleStatus(SaleStatus saleStatus) {
        try {
            String query = "INSERT INTO client_types (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, saleStatus.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SaleStatus added successfully!");
            } else {
                System.out.println("SaleStatus addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateSaleStatus(SaleStatus saleStatus) {
        String query = "UPDATE client_types SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, saleStatus.getName());
            ps.setInt(2, saleStatus.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SaleStatus updated successfully!");
            }else {
                System.out.println("SaleStatus update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public SaleStatus deleteSaleStatus(int id) {
        SaleStatus saleStatus = null;
        String selectQuery = "SELECT * FROM client_types WHERE id = ?";
        String deleteQuery = "DELETE FROM client_types WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    saleStatus = new SaleStatus(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (saleStatus != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("SaleStatus deleted successfully!");
                    return saleStatus;
                }
            }
            
            System.out.println("SaleStatus deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<SaleStatus> findSaleStatusById(int id) {
        String query = "SELECT id,name FROM client_types WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    SaleStatus saleStatus = new SaleStatus(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(saleStatus);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<SaleStatus> findSaleStatusByName(String name) {
        String query = "SELECT id,name FROM client_types WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    SaleStatus saleStatus = new SaleStatus(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(saleStatus);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<SaleStatus> findAllSaleStatus() {
        List<SaleStatus> saleStatuss = new ArrayList<>();
        String query = "SELECT id,name FROM client_types";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                SaleStatus saleStatus = new SaleStatus(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                saleStatuss.add(saleStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return saleStatuss;
    }

}

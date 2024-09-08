package com.electrostoresystem.orderstatus.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;

public class OrderStatusRepository implements OrderStatusService{
    private Connection connection;
    
    public  OrderStatusRepository(){
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
    public void createOrderStatus(OrderStatus orderStatus) {
        try {
            String query = "INSERT INTO order_status (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, orderStatus.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("OrderStatus added successfully!");
            } else {
                System.out.println("OrderStatus addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateOrderStatus(OrderStatus orderStatus) {
        String query = "UPDATE order_status SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, orderStatus.getName());
            ps.setInt(2, orderStatus.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("OrderStatus updated successfully!");
            }else {
                System.out.println("OrderStatus update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public OrderStatus deleteOrderStatus(int id) {
        OrderStatus orderStatus = null;
        String selectQuery = "SELECT * FROM order_status WHERE id = ?";
        String deleteQuery = "DELETE FROM order_status WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    orderStatus = new OrderStatus(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (orderStatus != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("OrderStatus deleted successfully!");
                    return orderStatus;
                }
            }
            
            System.out.println("OrderStatus deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<OrderStatus> findOrderStatusById(int id) {
        String query = "SELECT id,name FROM order_status WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    OrderStatus orderStatus = new OrderStatus(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(orderStatus);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrderStatus> findOrderStatusByName(String name) {
        String query = "SELECT id,name FROM order_status WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    OrderStatus orderStatus = new OrderStatus(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(orderStatus);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<OrderStatus> findAllOrderStatus() {
        List<OrderStatus> orderStatuss = new ArrayList<>();
        String query = "SELECT id,name FROM order_status";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                OrderStatus orderStatus = new OrderStatus(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                orderStatuss.add(orderStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderStatuss;
    }

}

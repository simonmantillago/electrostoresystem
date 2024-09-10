package com.electrostoresystem.order.infrastructure;

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

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;

public class OrderRepository implements OrderService {
    private Connection connection;
    
    public  OrderRepository(){
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
    public void createOrder(Order order) {
        try {
            String query = "INSERT INTO orders (supplier_id,status_id,payment_method) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, order.getSupplierId());
            ps.setInt(2, order.getStatusId());
            ps.setInt(3, order.getPayMethod());
            

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("order added successfully!");
            } else {
                System.out.println("order addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder(Order order) {
        String query = "UPDATE orders SET supplier_id = ?, status_id = ?, payment_method = ?, total = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, order.getSupplierId());
            ps.setInt(2, order.getStatusId());
            ps.setInt(3, order.getPayMethod());
            ps.setFloat(4, order.getTotal());  
            ps.setInt(5, order.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("order updated successfully!");
                JOptionPane.showMessageDialog(null,"Order updated successfully");
            }else {
                System.out.println("order update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order deleteOrder(int id) {
        Order order = null;
        String selectQuery = "SELECT * FROM orders WHERE id = ?";
        String deleteQuery = "DELETE FROM orders WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    order = new Order(
                        rs.getInt("id"),
                        rs.getString("order_date"),
                        rs.getString("supplier_id"),
                        rs.getInt("status_id"),
                        rs.getInt("payment_method"),
                        rs.getFloat("total")
    
                    );
                }
            }
            
            // If customer exists, delete it
            if (order != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Order deleted successfully!");
                    return order;
                }
            }
            
            System.out.println("Order deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Order> findOrderById(int id) {
       String query = "SELECT id, order_date, supplier_id, status_id, payment_method, total FROM orders WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("order_date"),
                        rs.getString("supplier_id"),
                        rs.getInt("status_id"),
                        rs.getInt("payment_method"),
                        rs.getFloat("total")
                    );
                    return Optional.of(order);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAllOrder() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, order_date, supplier_id, status_id, payment_method, total FROM orders";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getString("order_date"),
                    rs.getString("supplier_id"),
                    rs.getInt("status_id"),
                    rs.getInt("payment_method"),
                    rs.getFloat("total")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orders;
    }

}

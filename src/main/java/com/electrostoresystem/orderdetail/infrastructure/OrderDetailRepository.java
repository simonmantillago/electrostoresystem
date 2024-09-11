package com.electrostoresystem.orderdetail.infrastructure;

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

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class OrderDetailRepository implements OrderDetailService {
    private Connection connection;
    
    public  OrderDetailRepository(){
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
    public void createOrderDetail(OrderDetail orderDetail) {
        try {
            String query = "INSERT INTO order_details (order_id, product_id, quantity,unit_price) VALUES (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductId());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setFloat(4, orderDetail.getUnitPrice());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("orderDetail added successfully!");
                JOptionPane.showMessageDialog(null, "Order Created!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                System.out.println("orderDetail addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Not enough stock, or Product not Registered" );
        }
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {
        String query = "UPDATE order_details SET quantity = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, orderDetail.getQuantity());
            ps.setInt(2, orderDetail.getId());
    
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("orderDetail updated successfully!");
                JOptionPane.showMessageDialog(null,"OrderDetail updated successfully");
            }else {
                System.out.println("orderDetail update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderDetail deleteOrderDetail(int id) {
        OrderDetail orderDetail = null;
        String selectQuery = "SELECT * FROM order_details WHERE id = ?";
        String deleteQuery = "DELETE FROM order_details WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    orderDetail = new OrderDetail(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("subtotal")
                    );
                }
            }
            
            // If customer exists, delete it
            if (orderDetail != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("OrderDetail deleted successfully!");
                    return orderDetail;
                }
            }
            
            System.out.println("OrderDetail deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<OrderDetail> findOrderDetailById(int id) {
        String query = "SELECT id, order_id, product_id, quantity, unit_price, subtotal FROM order_details WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    OrderDetail orderDetail = new OrderDetail(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("subtotal")
                    );
                    return Optional.of(orderDetail);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<OrderDetail> findAllOrderDetail() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT id, order_id, product_id, quantity, unit_price, subtotal FROM order_details";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                    rs.getInt("id"),
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getFloat("unit_price"),
                    rs.getFloat("subtotal")
                );
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderDetails;
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT id, order_id, product_id, quantity, unit_price, subtotal FROM order_details WHERE order_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail orderDetail = new OrderDetail(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("subtotal")
                    );
                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderDetails;
    }

    @Override
    public void deleteOrderDetailsByOrderId(int orderId) {
        String deleteQuery = "DELETE FROM order_details WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("OrderDetails deleted successfully!");
            } else {
                System.out.println("No OrderDetails found for the given client_id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

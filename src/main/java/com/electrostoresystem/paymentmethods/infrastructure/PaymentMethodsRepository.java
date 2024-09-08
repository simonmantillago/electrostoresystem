package com.electrostoresystem.paymentmethods.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;

public class PaymentMethodsRepository implements PaymentMethodsService{
    private Connection connection;
    
    public  PaymentMethodsRepository(){
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
    public void createPaymentMethods(PaymentMethods paymentMethods) {
        try {
            String query = "INSERT INTO payment_methods (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, paymentMethods.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("PaymentMethods added successfully!");
            } else {
                System.out.println("PaymentMethods addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updatePaymentMethods(PaymentMethods paymentMethods) {
        String query = "UPDATE payment_methods SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, paymentMethods.getName());
            ps.setInt(2, paymentMethods.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("PaymentMethods updated successfully!");
            }else {
                System.out.println("PaymentMethods update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public PaymentMethods deletePaymentMethods(int id) {
        PaymentMethods paymentMethods = null;
        String selectQuery = "SELECT * FROM payment_methods WHERE id = ?";
        String deleteQuery = "DELETE FROM payment_methods WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    paymentMethods = new PaymentMethods(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (paymentMethods != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("PaymentMethods deleted successfully!");
                    return paymentMethods;
                }
            }
            
            System.out.println("PaymentMethods deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<PaymentMethods> findPaymentMethodsById(int id) {
        String query = "SELECT id,name FROM payment_methods WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    PaymentMethods paymentMethods = new PaymentMethods(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(paymentMethods);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<PaymentMethods> findPaymentMethodsByName(String name) {
        String query = "SELECT id,name FROM payment_methods WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    PaymentMethods paymentMethods = new PaymentMethods(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(paymentMethods);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<PaymentMethods> findAllPaymentMethods() {
        List<PaymentMethods> paymentMethodss = new ArrayList<>();
        String query = "SELECT id,name FROM payment_methods";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                PaymentMethods paymentMethods = new PaymentMethods(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                paymentMethodss.add(paymentMethods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return paymentMethodss;
    }

}

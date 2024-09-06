package com.electrostoresystem.clientphone.infrastructure;

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

import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;

public class ClientPhoneRepository implements ClientPhoneService {
private Connection connection;

    public ClientPhoneRepository() {
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
    public void createClientPhone(ClientPhone clientPhone) {
       try {
            String query = "INSERT INTO client_phones (phone,client_id) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, clientPhone.getPhone());
            ps.setString(2, clientPhone.getClientId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("clientPhone added successfully!");
                JOptionPane.showMessageDialog(null, "ClientPhone added successfully!");
            } else {
                System.out.println("clientPhone addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error, Wrong Id or client not Registered!");
        }
    }

    @Override
    public void updateClientPhone(ClientPhone clientPhone,String originalPhone) {
        String query = "UPDATE client_phones SET phone = ?, client_id = ? WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientPhone.getPhone());
            ps.setString(2, clientPhone.getClientId());
            ps.setString(3, originalPhone);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {     
                System.out.println("ClientPhone updated successfully!");
                JOptionPane.showMessageDialog(null, "ClientPhone updated successfully!");
            } else {  
                System.out.println("ClientPhone update failed!");
                JOptionPane.showMessageDialog(null, "ClientPhone update failed!");

                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ClientPhone update failed!");
            
        }
    }

    @Override
    public ClientPhone deleteClientPhone(String Phone) {
        ClientPhone clientPhone = null;
        String selectQuery = "SELECT * FROM client_phones WHERE phone = ?";
        String deleteQuery = "DELETE FROM client_phones WHERE phone = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
             PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {

            // First, fetch the clientPhone
            selectPs.setString(1, Phone);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    clientPhone = new ClientPhone(
                        rs.getString("client_id"),
                        rs.getNString("phone")
                        );
                }
            }

            // If clientPhone exists, delete it
            if (clientPhone != null) {
                deletePs.setString(1, Phone);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("ClientPhone deleted successfully!");
                    return clientPhone;
                }
            }

            System.out.println("ClientPhone deletion failed. ClientPhone not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<ClientPhone> findAllClientPhone() {
        List<ClientPhone> clientPhones = new ArrayList<>();
        String query = "SELECT phone,client_id FROM client_phones";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ClientPhone clientPhone = new ClientPhone(
                    rs.getString("phone"),
                    rs.getString("client_id")
                );
                clientPhones.add(clientPhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientPhones;
    }

    @Override
    public List<ClientPhone> findClientPhonesByClientId(String clientId) {
        List<ClientPhone> clientPhones = new ArrayList<>();
        String query = "SELECT phone, client_id FROM client_phones WHERE client_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClientPhone clientPhone = new ClientPhone(
                        rs.getString("client_id"),
                        rs.getString("phone")
                    );
                    clientPhones.add(clientPhone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientPhones;
    }

    @Override
    public Optional<ClientPhone> findClientPhoneByPhone(String Phone) {
            String query = "SELECT phone,client_id FROM client_phones WHERE phone = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, Phone);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ClientPhone clientPhone = new ClientPhone(
                            rs.getString("client_id"),
                            rs.getString("phone")
                        );
                        return Optional.of(clientPhone);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }
}

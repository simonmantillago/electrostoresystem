package com.electrostoresystem.client.infrastructure;

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

import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;

public class ClientRepository implements ClientService {
    private Connection connection;
    
    public  ClientRepository(){
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
    public void createClient(Client client) {
        try {
            String query = "INSERT INTO clients (id, name, type_id, client_type, email, city_id, address_details) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, client.getId());
            ps.setString(2, client.getName());
            ps.setInt(3, client.getTypeId());
            ps.setInt(4, client.getClientTypeId());
            ps.setString(5, client.getEmail());
            ps.setInt(6, client.getCityId());
            ps.setString(7, client.getAddressDetails());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("client added successfully!");
            } else {
                System.out.println("client addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client client) {
        String query = "UPDATE clients SET name = ?, type_id = ?, client_type = ?, email = ?,city_id = ?, address_details = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, client.getName());
            ps.setInt(2, client.getTypeId());
            ps.setInt(3, client.getClientTypeId());
            ps.setString(4, client.getEmail());
            ps.setInt(5, client.getCityId());
            ps.setString(6, client.getAddressDetails());
            ps.setString(7, client.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("client updated successfully!");
                JOptionPane.showMessageDialog(null,"Client updated successfully");
            }else {
                System.out.println("client update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client deleteClient(String id) {
        Client client = null;
        String selectQuery = "SELECT * FROM clients WHERE id = ?";
        String deleteQuery = "DELETE FROM clients WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setString(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    client = new Client(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("type_id"),
                        rs.getInt("client_type"),
                        rs.getString("email"),
                        rs.getInt("city_id"),
                        rs.getString("address_details")
                    );
                }
            }
            
            // If customer exists, delete it
            if (client != null) {
                deletePs.setString(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Client deleted successfully!");
                    return client;
                }
            }
            
            System.out.println("Client deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Client> findClientById(String id) {
        String query = "SELECT id, name, type_id, client_type, email, city_id, address_details FROM clients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Client client = new Client(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("type_id"),
                        rs.getInt("client_type"),
                        rs.getString("email"),
                        rs.getInt("city_id"),
                        rs.getString("address_details")
                    );
                    return Optional.of(client);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAllClient() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT id, name, type_id, client_type, email, city_id, address_details FROM clients";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Client client = new Client(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("type_id"),
                    rs.getInt("client_type"),
                    rs.getString("email"),
                    rs.getInt("city_id"),
                    rs.getString("address_details")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clients;
    }

}

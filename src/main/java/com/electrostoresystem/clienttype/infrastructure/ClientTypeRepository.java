package com.electrostoresystem.clienttype.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;

public class ClientTypeRepository implements ClientTypeService{
    private Connection connection;
    
    public  ClientTypeRepository(){
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
    public void createClientType(ClientType clientType) {
        try {
            String query = "INSERT INTO client_types (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, clientType.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("ClientType added successfully!");
            } else {
                System.out.println("ClientType addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateClientType(ClientType clientType) {
        String query = "UPDATE client_types SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, clientType.getName());
            ps.setInt(2, clientType.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("ClientType updated successfully!");
            }else {
                System.out.println("ClientType update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public ClientType deleteClientType(int id) {
        ClientType clientType = null;
        String selectQuery = "SELECT * FROM client_types WHERE id = ?";
        String deleteQuery = "DELETE FROM client_types WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    clientType = new ClientType(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (clientType != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("ClientType deleted successfully!");
                    return clientType;
                }
            }
            
            System.out.println("ClientType deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<ClientType> findClientTypeById(int id) {
        String query = "SELECT id,name FROM client_types WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    ClientType clientType = new ClientType(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(clientType);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClientType> findClientTypeByName(String name) {
        String query = "SELECT id,name FROM client_types WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    ClientType clientType = new ClientType(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(clientType);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<ClientType> findAllClientType() {
        List<ClientType> clientTypes = new ArrayList<>();
        String query = "SELECT id,name FROM client_types";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ClientType clientType = new ClientType(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                clientTypes.add(clientType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientTypes;
    }

}

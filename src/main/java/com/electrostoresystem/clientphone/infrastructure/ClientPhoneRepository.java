package com.electrostoresystem.clientphone.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;



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
            } else {
                System.out.println("clientPhone addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClientPhone(ClientPhone clientPhone) {
        String query = "UPDATE client_phones SET phone = ?, client_id = ? WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientPhone.getPhone());
            ps.setString(2, clientPhone.getClientId());
            ps.setString(3, clientPhone.getPhone());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {     
                System.out.println("ClientPhone updated successfully!");
            } else {  
                System.out.println("ClientPhone update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                        rs.getNString("phone"),
                        rs.getString("client_id")
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
    public Optional<ClientPhone> findClientPhoneByPhone(String ClientPhoneId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findClientPhoneById'");
    }

    @Override
    public List<ClientPhone> findAllClientPhone() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllClientPhone'");
    }
}

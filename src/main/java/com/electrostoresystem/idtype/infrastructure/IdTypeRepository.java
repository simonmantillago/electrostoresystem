package com.electrostoresystem.idtype.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;

public class IdTypeRepository implements IdTypeService{
    private Connection connection;
    
    public  IdTypeRepository(){
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
    public void createIdType(IdType idType) {
        try {
            String query = "INSERT INTO id_types (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, idType.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("IdType added successfully!");
            } else {
                System.out.println("IdType addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateIdType(IdType idType) {
        String query = "UPDATE id_types SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, idType.getName());
            ps.setInt(2, idType.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("IdType updated successfully!");
            }else {
                System.out.println("IdType update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public IdType deleteIdType(int id) {
        IdType idType = null;
        String selectQuery = "SELECT * FROM id_types WHERE id = ?";
        String deleteQuery = "DELETE FROM id_types WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    idType = new IdType(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (idType != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("IdType deleted successfully!");
                    return idType;
                }
            }
            
            System.out.println("IdType deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<IdType> findIdTypeById(int id) {
        String query = "SELECT id,name FROM id_types WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    IdType idType = new IdType(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(idType);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<IdType> findIdTypeByName(String name) {
        String query = "SELECT id,name FROM id_types WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    IdType idType = new IdType(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(idType);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<IdType> findAllIdType() {
        List<IdType> idTypes = new ArrayList<>();
        String query = "SELECT id,name FROM id_types";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                IdType idType = new IdType(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                idTypes.add(idType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return idTypes;
    }

}

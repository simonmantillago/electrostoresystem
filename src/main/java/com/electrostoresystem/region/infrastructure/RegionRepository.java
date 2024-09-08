package com.electrostoresystem.region.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

public class RegionRepository implements RegionService{
    private Connection connection;
    
    public  RegionRepository(){
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
    public void createRegion(Region region) {
        try {
            String query = "INSERT INTO regions (name,country_id) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, region.getName());
            ps.setInt(2, region.getCountryId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Region added successfully!");
            } else {
                System.out.println("Region addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateRegion(Region region) {
        String query = "UPDATE regions SET name = ?, country_id = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, region.getName());
            ps.setInt(2, region.getCountryId());
            ps.setInt(3, region.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Region updated successfully!");
            }else {
                System.out.println("Region update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public Region deleteRegion(int id) {
        Region region = null;
        String selectQuery = "SELECT * FROM regions WHERE id = ?";
        String deleteQuery = "DELETE FROM regions WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    region = new Region(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("country_id")
                    );
                }
            }
            
            // If customer exists, delete it
            if (region != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Region deleted successfully!");
                    return region;
                }
            }
            
            System.out.println("Region deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Region> findRegionById(int id) {
        String query = "SELECT id,name,country_id FROM regions WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Region region = new Region(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("country_id")
                    );
                    return Optional.of(region);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Region> findRegionByName(String name) {
        String query = "SELECT id,name,country_id FROM regions WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Region region = new Region(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("country_id")
                    );
                    return Optional.of(region);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<Region> findAllRegion() {
        List<Region> regions = new ArrayList<>();
        String query = "SELECT id,name,country_id FROM regions";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Region region = new Region(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("country_id")
                );
                regions.add(region);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return regions;
    }


    @Override
    public List<Region> findRegionsByCountry(int countryId) {
        List<Region> regions = new ArrayList<>();
        String query = "SELECT id,name,country_id FROM regions WHERE country_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, countryId);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Region region = new Region(
                        rs.getInt("id"),
                        rs.getString("name"),
                        countryId
                    );
                    regions.add(region);
            
                    
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regions;
    }

}

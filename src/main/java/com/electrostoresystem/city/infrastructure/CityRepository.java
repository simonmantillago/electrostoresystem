package com.electrostoresystem.city.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

public class CityRepository implements CityService{
    private Connection connection;
    
    public  CityRepository(){
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
    public void createCity(City city) {
        try {
            String query = "INSERT INTO cities (name,region_id) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, city.getName());
            ps.setInt(2, city.getRegionId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("City added successfully!");
            } else {
                System.out.println("City addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateCity(City city) {
        String query = "UPDATE cities SET name = ?, region_id = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, city.getName());
            ps.setInt(2, city.getRegionId());
            ps.setInt(3, city.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("City updated successfully!");
            }else {
                System.out.println("City update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public City deleteCity(int id) {
        City city = null;
        String selectQuery = "SELECT * FROM cities WHERE id = ?";
        String deleteQuery = "DELETE FROM cities WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id")
                    );
                }
            }
            
            // If customer exists, delete it
            if (city != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("City deleted successfully!");
                    return city;
                }
            }
            
            System.out.println("City deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<City> findCityById(int id) {
        String query = "SELECT id,name,region_id FROM cities WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id")
                    );
                    return Optional.of(city);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<City> findCityByName(String name) {
        String query = "SELECT id,name,region_id FROM cities WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id")
                    );
                    return Optional.of(city);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<City> findAllCity() {
        List<City> citys = new ArrayList<>();
        String query = "SELECT id,name,region_id FROM cities";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                City city = new City(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("region_id")
                );
                citys.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return citys;
    }


    @Override
    public List<City> findCitysByRegion(int regionId) {
        List<City> citys = new ArrayList<>();
        String query = "SELECT id,name,region_id FROM cities WHERE region_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, regionId);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        regionId
                    );
                    citys.add(city);
            
                    
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citys;
    }




}

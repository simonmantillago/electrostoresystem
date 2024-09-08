package com.electrostoresystem.country.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;

public class CountryRepository implements CountryService{
    private Connection connection;
    
    public  CountryRepository(){
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
    public void createCountry(Country country) {
        try {
            String query = "INSERT INTO countries (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, country.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Country added successfully!");
            } else {
                System.out.println("Country addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateCountry(Country country) {
        String query = "UPDATE countries SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, country.getName());
            ps.setInt(2, country.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Country updated successfully!");
            }else {
                System.out.println("Country update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public Country deleteCountry(int id) {
        Country country = null;
        String selectQuery = "SELECT * FROM countries WHERE id = ?";
        String deleteQuery = "DELETE FROM countries WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    country = new Country(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (country != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Country deleted successfully!");
                    return country;
                }
            }
            
            System.out.println("Country deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Country> findCountryById(int id) {
        String query = "SELECT id,name FROM countries WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Country country = new Country(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(country);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Country> findCountryByName(String name) {
        String query = "SELECT id,name FROM countries WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Country country = new Country(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(country);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<Country> findAllCountry() {
        List<Country> countrys = new ArrayList<>();
        String query = "SELECT id,name FROM countries";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Country country = new Country(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                countrys.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return countrys;
    }

}

package com.electrostoresystem.brand.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;

public class BrandRepository implements BrandService{
    private Connection connection;
    
    public  BrandRepository(){
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
    public void createBrand(Brand brand) {
        try {
            String query = "INSERT INTO brands (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, brand.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Brand added successfully!");
            } else {
                System.out.println("Brand addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateBrand(Brand brand) {
        String query = "UPDATE brands SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, brand.getName());
            ps.setInt(2, brand.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Brand updated successfully!");
            }else {
                System.out.println("Brand update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public Brand deleteBrand(int id) {
        Brand brand = null;
        String selectQuery = "SELECT * FROM brands WHERE id = ?";
        String deleteQuery = "DELETE FROM brands WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    brand = new Brand(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (brand != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Brand deleted successfully!");
                    return brand;
                }
            }
            
            System.out.println("Brand deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Brand> findBrandById(int id) {
        String query = "SELECT id,name FROM brands WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Brand brand = new Brand(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(brand);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Brand> findBrandByName(String name) {
        String query = "SELECT id,name FROM brands WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Brand brand = new Brand(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(brand);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<Brand> findAllBrand() {
        List<Brand> brands = new ArrayList<>();
        String query = "SELECT id,name FROM brands";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Brand brand = new Brand(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return brands;
    }

}

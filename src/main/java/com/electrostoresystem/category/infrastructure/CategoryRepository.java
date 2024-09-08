package com.electrostoresystem.category.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;

public class CategoryRepository implements CategoryService{
    private Connection connection;
    
    public  CategoryRepository(){
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
    public void createCategory(Category category) {
        try {
            String query = "INSERT INTO categories (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, category.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category added successfully!");
            } else {
                System.out.println("Category addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateCategory(Category category) {
        String query = "UPDATE categories SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category updated successfully!");
            }else {
                System.out.println("Category update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public Category deleteCategory(int id) {
        Category category = null;
        String selectQuery = "SELECT * FROM categories WHERE id = ?";
        String deleteQuery = "DELETE FROM categories WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
            
            // If customer exists, delete it
            if (category != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Category deleted successfully!");
                    return category;
                }
            }
            
            System.out.println("Category deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Category> findCategoryById(int id) {
        String query = "SELECT id,name FROM categories WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(category);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        String query = "SELECT id,name FROM categories WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                    return Optional.of(category);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    @Override
    public List<Category> findAllCategory() {
        List<Category> categorys = new ArrayList<>();
        String query = "SELECT id,name FROM categories";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                categorys.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categorys;
    }

}

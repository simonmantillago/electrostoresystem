package com.electrostoresystem.product.infrastructure;

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

import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;

public class ProductRepository implements ProductService {
    private Connection connection;
    
    public  ProductRepository(){
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
    public void createProduct(Product product) {
       try {
            String query = "INSERT INTO products (name, description, sale_price, stock, min_stock, category_id, brand_id) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setFloat(3, product.getSalePrice());
            ps.setInt(4, 0);
            ps.setInt(5, product.getMinStock());
            ps.setInt(6, product.getCategoryId());
            ps.setInt(7, product.getBrandId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("product added successfully!");
            } else {
                System.out.println("product addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        String query = "UPDATE products SET name = ?,  description = ?, sale_price = ?, stock = ?, min_stock = ?, category_id = ?, brand_id = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setFloat(3, product.getSalePrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getMinStock());
            ps.setInt(6, product.getCategoryId());
            ps.setInt(7, product.getBrandId());
            ps.setInt(8, product.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("product updated successfully!");
                JOptionPane.showMessageDialog(null,"Product updated successfully");
            }else {
                System.out.println("product update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product deleteProduct(int id) {
        Product product = null;
        String selectQuery = "SELECT * FROM products WHERE id = ?";
        String deleteQuery = "DELETE FROM products WHERE id = ?";
        
        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
            
            // First, fetch the customer
            selectPs.setInt(1, id);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("sale_price"),
                        rs.getInt("stock"),
                        rs.getInt("min_stock"),
                        rs.getInt("category_id"),
                        rs.getInt("brand_id")
                    );
                }
            }
            
            // If customer exists, delete it
            if (product != null) {
                deletePs.setInt(1, id);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Product deleted successfully!");
                    return product;
                }
            }
            
            System.out.println("Product deletion failed. Customer not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Product> findProductById(int id) {
        String query = "SELECT id, name, description, sale_price, stock, min_stock, category_id, brand_id FROM products WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("sale_price"),
                        rs.getInt("stock"),
                        rs.getInt("min_stock"),
                        rs.getInt("category_id"),
                        rs.getInt("brand_id")
                    );
                    return Optional.of(product);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, name, description, sale_price, stock, min_stock, category_id, brand_id FROM products";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getFloat("sale_price"),
                    rs.getInt("stock"),
                    rs.getInt("min_stock"),
                    rs.getInt("category_id"),
                    rs.getInt("brand_id")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

}

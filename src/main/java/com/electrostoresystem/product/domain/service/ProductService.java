package com.electrostoresystem.product.domain.service;

import java.util.List;
import java.util.Optional;

import com.electrostoresystem.product.domain.entity.Product;

public interface ProductService {
    void createProduct (Product product);
    void updateProduct (Product product);
    Product deleteProduct (int id);
    Optional<Product> findProductById(int id);
    List<Product> findAllProduct();
}

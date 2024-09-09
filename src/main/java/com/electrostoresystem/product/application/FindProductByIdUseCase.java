package com.electrostoresystem.product.application;

import java.util.Optional;

import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;

public class FindProductByIdUseCase {
    private final ProductService productService;

    public FindProductByIdUseCase(ProductService productService) {
        this.productService = productService;
    }

    public Optional<Product> execute(int id) {
        return productService.findProductById(id);
    }
}

package com.electrostoresystem.product.application;

import java.util.List;

import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;

public class FindAllProductUseCase {
    private final ProductService productService;

    public FindAllProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> execute() {
        return productService.findAllProduct();
    }
}

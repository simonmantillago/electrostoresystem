package com.electrostoresystem.product.application;

import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;

public class CreateProductUseCase {
    private final ProductService productService;

    public CreateProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public void execute(Product product){
        productService.createProduct(product);
    }

}

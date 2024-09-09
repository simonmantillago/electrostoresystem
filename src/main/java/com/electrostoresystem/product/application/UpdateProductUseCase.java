package com.electrostoresystem.product.application;

import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;

public class UpdateProductUseCase {
 private final ProductService productService;

    public UpdateProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public void execute(Product product){
        productService.updateProduct(product);
    }
}

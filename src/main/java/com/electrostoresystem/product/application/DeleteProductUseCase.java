package com.electrostoresystem.product.application;

import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;

public class DeleteProductUseCase {
    private final ProductService productService;

    public DeleteProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public Product execute(int codeProduct) {
        return productService.deleteProduct(codeProduct);
    }
}

package com.electrostoresystem.brand.application;

import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;

public class DeleteBrandUseCase {
    private final BrandService brandService;

    public DeleteBrandUseCase(BrandService brandService) {
        this.brandService = brandService;
    }

    public Brand execute(int codeBrand) {
        return brandService.deleteBrand(codeBrand);
    }
}

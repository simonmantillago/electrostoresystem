package com.electrostoresystem.brand.application;

import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;

public class UpdateBrandUseCase {
 private final BrandService brandService;

    public UpdateBrandUseCase(BrandService brandService) {
        this.brandService = brandService;
    }

    public void execute(Brand brand){
        brandService.updateBrand(brand);
    }
}

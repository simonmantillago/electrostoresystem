package com.electrostoresystem.brand.application;

import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;

public class CreateBrandUseCase {
    private final BrandService brandService;

    public CreateBrandUseCase(BrandService brandService) {
        this.brandService = brandService;
    }

    public void execute(Brand brand){
        brandService.createBrand(brand);
    }

}

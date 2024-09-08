package com.electrostoresystem.brand.application;

import java.util.List;

import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;

public class FindAllBrandUseCase {
    private final BrandService brandService;

    public FindAllBrandUseCase(BrandService brandService) {
        this.brandService = brandService;
    }

    public List<Brand> execute() {
        return brandService.findAllBrand();
    }
}

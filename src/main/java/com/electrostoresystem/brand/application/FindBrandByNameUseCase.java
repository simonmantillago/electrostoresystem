package com.electrostoresystem.brand.application;

import java.util.Optional;

import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;

public class FindBrandByNameUseCase {
    private final BrandService brandService;

    public FindBrandByNameUseCase(BrandService brandService) {
        this.brandService = brandService;
    }

    public Optional<Brand> execute(String name) {
        return brandService.findBrandByName(name);
    }
}

package com.electrostoresystem.brand.application;

import java.util.Optional;

import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;

public class FindBrandByIdUseCase {
    private final BrandService brandService;

    public FindBrandByIdUseCase(BrandService brandService) {
        this.brandService = brandService;
    }

    public Optional<Brand> execute(int id) {
        return brandService.findBrandById(id);
    }
}

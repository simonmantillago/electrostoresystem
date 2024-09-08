package com.electrostoresystem.brand.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.brand.domain.entity.Brand;

public interface BrandService {
    void createBrand (Brand brand);
    void updateBrand (Brand brand);
    Brand deleteBrand (int id);
    Optional<Brand> findBrandById(int id);
    Optional<Brand> findBrandByName(String name);
    List<Brand> findAllBrand();

}

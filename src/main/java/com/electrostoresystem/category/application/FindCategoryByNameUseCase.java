package com.electrostoresystem.category.application;

import java.util.Optional;

import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;

public class FindCategoryByNameUseCase {
    private final CategoryService categoryService;

    public FindCategoryByNameUseCase(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Optional<Category> execute(String name) {
        return categoryService.findCategoryByName(name);
    }
}

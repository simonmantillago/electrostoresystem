package com.electrostoresystem.category.application;

import java.util.Optional;

import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;

public class FindCategoryByIdUseCase {
    private final CategoryService categoryService;

    public FindCategoryByIdUseCase(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Optional<Category> execute(int id) {
        return categoryService.findCategoryById(id);
    }
}

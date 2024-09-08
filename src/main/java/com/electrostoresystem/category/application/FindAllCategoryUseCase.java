package com.electrostoresystem.category.application;

import java.util.List;

import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;

public class FindAllCategoryUseCase {
    private final CategoryService categoryService;

    public FindAllCategoryUseCase(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> execute() {
        return categoryService.findAllCategory();
    }
}

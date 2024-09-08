package com.electrostoresystem.category.application;

import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;

public class UpdateCategoryUseCase {
 private final CategoryService categoryService;

    public UpdateCategoryUseCase(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void execute(Category category){
        categoryService.updateCategory(category);
    }
}

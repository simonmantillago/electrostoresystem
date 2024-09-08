package com.electrostoresystem.category.application;

import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;

public class DeleteCategoryUseCase {
    private final CategoryService categoryService;

    public DeleteCategoryUseCase(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Category execute(int codeCategory) {
        return categoryService.deleteCategory(codeCategory);
    }
}

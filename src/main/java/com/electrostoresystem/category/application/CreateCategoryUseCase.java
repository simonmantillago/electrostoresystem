package com.electrostoresystem.category.application;

import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;

public class CreateCategoryUseCase {
    private final CategoryService categoryService;

    public CreateCategoryUseCase(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void execute(Category category){
        categoryService.createCategory(category);
    }

}

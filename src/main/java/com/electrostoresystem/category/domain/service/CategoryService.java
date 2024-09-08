package com.electrostoresystem.category.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.category.domain.entity.Category;

public interface CategoryService {
    void createCategory (Category category);
    void updateCategory (Category category);
    Category deleteCategory (int id);
    Optional<Category> findCategoryById(int id);
    Optional<Category> findCategoryByName(String name);
    List<Category> findAllCategory();

}

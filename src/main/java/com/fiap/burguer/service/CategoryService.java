package com.fiap.burguer.service;

import com.fiap.burguer.entities.Category;
import com.fiap.burguer.repository.CategoryRepository;

public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategoryOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(int id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }
}

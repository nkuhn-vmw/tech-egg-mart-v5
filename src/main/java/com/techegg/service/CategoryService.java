package com.techegg.service;

import com.techegg.model.Category;
import com.techegg.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public Category createCategory(Category category) {
        category.setId(null);
        return categoryRepository.save(category);
    }

    @Transactional
    public Optional<Category> updateCategory(Long id, Category updated) {
        return categoryRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            return categoryRepository.save(existing);
        });
    }

    @Transactional
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.techegg.service;

import com.techegg.domain.Category;
import com.techegg.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setName("Books");
        category.setDescription("All kinds of books");
        category = categoryRepository.save(category);
    }

    @Test
    void testFindAll() {
        assertThat(categoryService.findAll()).isNotEmpty();
    }

    @Test
    void testFindBySlug() {
        // Assuming slug is generated from name elsewhere; set manually for test
        category.setSlug("books");
        categoryRepository.save(category);
        Category found = categoryService.findBySlug("books");
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Books");
    }

    @Test
    void testSaveAndDelete() {
        Category newCat = new Category();
        newCat.setName("Games");
        newCat.setDescription("Video games");
        Category saved = categoryService.save(newCat);
        assertThat(saved.getId()).isNotNull();
        categoryService.deleteById(saved.getId());
        assertThat(categoryService.findById(saved.getId())).isEmpty();
    }
}

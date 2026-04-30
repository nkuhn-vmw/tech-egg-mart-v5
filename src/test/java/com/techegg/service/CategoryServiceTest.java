package com.techegg.service;

import com.techegg.domain.Category;
import com.techegg.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Category c = new Category();
        c.setId(1L);
        when(categoryRepository.findAll()).thenReturn(List.of(c));
        List<Category> result = categoryService.findAll();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testFindById() {
        Category c = new Category();
        c.setId(2L);
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(c));
        Optional<Category> opt = categoryService.findById(2L);
        assertTrue(opt.isPresent());
        assertEquals(2L, opt.get().getId());
    }

    @Test
    void testFindBySlug() {
        Category c = new Category();
        c.setSlug("electronics");
        when(categoryRepository.findBySlug("electronics")).thenReturn(c);
        Category result = categoryService.findBySlug("electronics");
        assertNotNull(result);
        assertEquals("electronics", result.getSlug());
    }
}

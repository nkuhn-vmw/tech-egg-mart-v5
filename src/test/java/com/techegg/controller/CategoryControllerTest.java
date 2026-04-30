package com.techegg.controller;

import com.techegg.domain.Category;
import com.techegg.domain.Product;
import com.techegg.service.CategoryService;
import com.techegg.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testViewCategory() {
        Category cat = new Category();
        cat.setId(1L);
        cat.setName("Electronics");
        cat.setSlug("electronics");
        Product p = new Product();
        p.setId(10L);
        when(categoryService.findBySlug("electronics")).thenReturn(cat);
        when(productService.findFiltered(eq(1L), any(), any())).thenReturn(List.of(p));
        when(categoryService.findAll()).thenReturn(List.of(cat));

        ModelMap model = new ModelMap();
        String view = categoryController.viewCategory("electronics", null, null, model);
        assertThat(view).isEqualTo("product-list");
        assertThat(model.get("products")).asList().containsExactly(p);
        assertThat(model.get("categories")).asList().containsExactly(cat);
        assertThat(model.get("selectedCategoryId")).isEqualTo(1L);
        assertThat(model.get("selectedCategoryName")).isEqualTo("Electronics");
    }
}

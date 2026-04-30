package com.techegg.controller;

import com.techegg.domain.Product;
import com.techegg.domain.Category;
import com.techegg.domain.Review;
import com.techegg.service.ProductService;
import com.techegg.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // configure view resolver so that view names can be resolved
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testListProducts() throws Exception {
        Product p = new Product();
        p.setId(1L);
        Category c = new Category();
        c.setId(2L);
        when(productService.findFiltered(null, null, null)).thenReturn(List.of(p));
        when(categoryService.findAll()).thenReturn(List.of(c));

        ModelMap model = new ModelMap();
        String view = productController.listProducts(null, null, null, model);
        assertThat(view).isEqualTo("product-list");
        assertThat(model.get("products")).asList().containsExactly(p);
        assertThat(model.get("categories")).asList().containsExactly(c);
    }

    @Test
    void testProductDetail() {
        Product p = new Product();
        p.setId(5L);
        Review r = new Review();
        r.setId(10L);
        when(productService.findById(5L)).thenReturn(java.util.Optional.of(p));
        when(productService.getReviewsForProduct(5L)).thenReturn(List.of(r));
        ModelMap model = new ModelMap();
        String view = productController.productDetail(5L, model);
        assertThat(view).isEqualTo("product-detail");
        assertThat(model.get("product")).isEqualTo(p);
        assertThat(model.get("reviews")).asList().containsExactly(r);
    }

    @Test
    void testAddReviewValidationError() {
        Review review = new Review();
        // simulate binding errors
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        // redirect attributes mock
        org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes = mock(org.springframework.web.servlet.mvc.support.RedirectAttributes.class);
        String result = productController.addReview(1L, review, bindingResult, redirectAttributes);
        assertThat(result).isEqualTo("redirect:/products/1");
        verify(redirectAttributes).addFlashAttribute(eq("error"), anyString());
    }

    @Test
    void testAddReviewSuccess() {
        Review review = new Review();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes = mock(org.springframework.web.servlet.mvc.support.RedirectAttributes.class);
        when(productService.addReview(eq(2L), any(Review.class))).thenReturn(review);
        String result = productController.addReview(2L, review, bindingResult, redirectAttributes);
        assertThat(result).isEqualTo("redirect:/products/2");
        verify(redirectAttributes).addFlashAttribute(eq("message"), anyString());
    }
}

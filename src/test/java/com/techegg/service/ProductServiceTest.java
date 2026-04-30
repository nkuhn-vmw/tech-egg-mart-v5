package com.techegg.service;

import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import com.techegg.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Product p1 = new Product();
        p1.setId(1L);
        when(productRepository.findAll()).thenReturn(List.of(p1));
        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testFindFilteredByCategory() {
        Product p = new Product();
        p.setId(2L);
        when(productRepository.findByCategoryId(5L)).thenReturn(List.of(p));
        List<Product> result = productService.findFiltered(5L, null, null);
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getId());
    }

    @Test
    void testAddReviewUpdatesProduct() {
        Product product = new Product();
        product.setId(10L);
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        Review review = new Review();
        review.setRating(4);
        review.setComment("Good");
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewRepository.findByProductId(10L)).thenReturn(List.of(review));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Review saved = productService.addReview(10L, review);
        assertNotNull(saved);
        // verify rating and count updated
        assertEquals(4.0, product.getRating());
        assertEquals(1, product.getReviewCount());
    }

    @Test
    void testAddReviewProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        Review review = new Review();
        review.setRating(5);
        assertThrows(ResourceNotFoundException.class, () -> productService.addReview(99L, review));
    }
}

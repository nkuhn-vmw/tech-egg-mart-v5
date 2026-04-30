package com.techegg.service;

import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository, productRepository);
    }

    @Test
    void testAddReview_Success() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setRating(0.0);
        product.setReviewCount(0);

        Review review = new Review();
        review.setReviewerName("John Doe");
        review.setRating(5);
        review.setComment("Great product!");
        review.setDate(Instant.now());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> {
            Review savedReview = invocation.getArgument(0);
            savedReview.setId(1L);
            return savedReview;
        });

        // When
        Review result = reviewService.addReview(productId, review);

        // Then
        assertNotNull(result.getId());
        assertEquals("John Doe", result.getReviewerName());
        assertEquals(5, result.getRating());
        assertEquals("Great product!", result.getComment());
        verify(productRepository).save(any(Product.class));
        verify(reviewRepository).save(review);
    }

    @Test
    void testAddReview_ProductNotFound() {
        // Given
        Long productId = 1L;
        Review review = new Review();
        review.setReviewerName("John Doe");
        review.setRating(5);
        review.setComment("Great product!");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.addReview(productId, review);
        });
    }

    @Test
    void testFindByProductId() {
        // Given
        Long productId = 1L;
        Review review1 = new Review();
        review1.setId(1L);
        review1.setReviewerName("John Doe");
        review1.setRating(5);
        review1.setComment("Great product!");

        Review review2 = new Review();
        review2.setId(2L);
        review2.setReviewerName("Jane Smith");
        review2.setRating(4);
        review2.setComment("Good product!");

        when(reviewRepository.findByProductId(productId)).thenReturn(Arrays.asList(review1, review2));

        // When
        var result = reviewService.findByProductId(productId);

        // Then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getReviewerName());
        assertEquals("Jane Smith", result.get(1).getReviewerName());
        verify(reviewRepository).findByProductId(productId);
    }
}
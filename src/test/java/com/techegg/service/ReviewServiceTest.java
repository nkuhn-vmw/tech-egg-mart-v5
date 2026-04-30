package com.techegg.service;

import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    private Product product;
    private Review review;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setDescription("Desc");
        product.setPrice(java.math.BigDecimal.valueOf(10.0));
        product.setImageUrl("http://example.com/img.jpg");
        product = productRepository.save(product);

        review = new Review();
        review.setReviewerName("Alice");
        review.setRating(4);
        review.setComment("Good");
        review.setDate(Instant.now());
        review.setProduct(product);
    }

    @Test
    void testSaveAndFind() {
        Review saved = reviewService.save(review);
        assertThat(saved.getId()).isNotNull();
        assertThat(reviewService.findById(saved.getId())).isPresent();
    }

    @Test
    void testFindByProductId() {
        reviewService.save(review);
        assertThat(reviewService.findByProductId(product.getId()))
                .extracting(Review::getReviewerName)
                .containsExactly("Alice");
    }

    @Test
    void testDelete() {
        Review saved = reviewService.save(review);
        reviewService.deleteById(saved.getId());
        assertThat(reviewService.findById(saved.getId())).isEmpty();
    }
}

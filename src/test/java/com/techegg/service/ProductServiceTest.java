package com.techegg.service;

import com.techegg.domain.Category;
import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.repository.CategoryRepository;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic items");
        category = categoryRepository.save(category);

        product = new Product();
        product.setName("Smartphone");
        product.setDescription("A cool smartphone");
        product.setPrice(BigDecimal.valueOf(199.99));
        product.setImageUrl("http://example.com/img.png");
        product.setCategory(category);
        product = productRepository.save(product);
    }

    @Test
    void testFindAll() {
        List<Product> products = productService.findAll();
        assertThat(products).isNotEmpty();
        assertThat(products).extracting("name").contains("Smartphone");
    }

    @Test
    void testAddReviewUpdatesRatingAndCount() {
        Review review = new Review();
        review.setRating(5);
        review.setComment("Great product");
        review.setReviewerName("John Doe");
        productService.addReview(product.getId(), review);

        Product updated = productService.findById(product.getId()).orElseThrow();
        assertThat(updated.getReviewCount()).isEqualTo(1);
        assertThat(updated.getRating()).isEqualTo(5.0);
    }

    @Test
    void testFilteredByCategory() {
        List<Product> filtered = productService.findFiltered(category.getId(), null, null);
        assertThat(filtered).hasSize(1);
        assertThat(filtered.get(0).getName()).isEqualTo("Smartphone");
    }
}

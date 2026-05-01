package com.techegg.service;

import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Create a new product.
     */
    @Transactional
    public Product createProduct(Product product) {
        // Additional business logic can be added here (e.g., checking duplicates)
        return productRepository.save(product);
    }

    /**
     * Retrieve a product by its ID.
     */
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Retrieve all products.
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Update an existing product.
     */
    @Transactional
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existing -> {
            // Update fields – only non-null values are applied to avoid overwriting unintentionally
            if (updatedProduct.getName() != null) existing.setName(updatedProduct.getName());
            if (updatedProduct.getDescription() != null) existing.setDescription(updatedProduct.getDescription());
            if (updatedProduct.getBrand() != null) existing.setBrand(updatedProduct.getBrand());
            if (updatedProduct.getModel() != null) existing.setModel(updatedProduct.getModel());
            if (updatedProduct.getSpecifications() != null) existing.setSpecifications(updatedProduct.getSpecifications());
            if (updatedProduct.getPrice() != null) existing.setPrice(updatedProduct.getPrice());
            if (updatedProduct.getImageUrl() != null) existing.setImageUrl(updatedProduct.getImageUrl());
            if (updatedProduct.getStockQuantity() != null) existing.setStockQuantity(updatedProduct.getStockQuantity());
            if (updatedProduct.getCategory() != null) existing.setCategory(updatedProduct.getCategory());
            // Reviews are managed through ReviewService; we don't replace the whole list here.
            return productRepository.save(existing);
        });
    }

    /**
     * Delete a product by its ID.
     */
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Add a review to a product and recalculate average rating.
     */
    @Transactional
    public Review addReview(Long productId, Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        // Associate review with product
        review.setProduct(product);
        Review saved = reviewRepository.save(review);
        // Recalculate average rating
        recalculateRating(product);
        return saved;
    }

    /**
     * Recalculate the average rating for a product based on its reviews.
     */
    private void recalculateRating(Product product) {
        List<Review> reviews = product.getReviews();
        if (reviews == null || reviews.isEmpty()) {
            product.setAverageRating(null);
            product.setReviewCount(0);
        } else {
            double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
            product.setAverageRating(BigDecimal.valueOf(avg).setScale(2, BigDecimal.ROUND_HALF_UP));
            product.setReviewCount(reviews.size());
        }
        productRepository.save(product);
    }
}

package com.techegg.service;

import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing product reviews.
 * Provides CRUD operations and basic business logic such as linking a review to a product.
 */
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    /**
     * Add a new review for a given product.
     *
     * @param productId the id of the product to which the review belongs
     * @param review    the review entity (authorName, rating, comment, etc.)
     * @return the persisted Review
     * @throws IllegalArgumentException if the product does not exist
     */
    public Review addReview(Long productId, Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        // Ensure bidirectional relationship is kept in sync
        product.addReview(review);
        // review.setProduct(product); // handled by addReview helper
        return reviewRepository.save(review);
    }

    /**
     * Retrieve all reviews for a specific product.
     */
    public List<Review> getReviewsByProductId(Long productId) {
        // Optionally verify product exists; if not, return empty list
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product not found with id: " + productId);
        }
        return reviewRepository.findByProductId(productId);
    }

    /**
     * Get a single review by its id.
     */
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));
    }

    /**
     * Update an existing review. Only mutable fields (authorName, rating, comment) are updated.
     */
    public Review updateReview(Long reviewId, Review updated) {
        Review existing = getReview(reviewId);
        existing.setAuthorName(updated.getAuthorName());
        existing.setRating(updated.getRating());
        existing.setComment(updated.getComment());
        // createdAt is not changed; product association remains the same.
        return reviewRepository.save(existing);
    }

    /**
     * Delete a review by its id.
     */
    public void deleteReview(Long reviewId) {
        Review existing = getReview(reviewId);
        // Remove from product's collection to keep both sides consistent
        Product product = existing.getProduct();
        if (product != null) {
            product.removeReview(existing);
        }
        reviewRepository.delete(existing);
    }
}

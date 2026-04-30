package com.techegg.service;

import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findFiltered(Long categoryId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice) {
        if (categoryId != null && minPrice != null && maxPrice != null) {
            return productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);
        } else if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId);
        } else if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice);
        } else {
            return productRepository.findAll();
        }
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional
    public Review addReview(Long productId, Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        review.setProduct(product);
        Review saved = reviewRepository.save(review);
        // Update product aggregate rating and review count (simple implementation)
        List<Review> reviews = reviewRepository.findByProductId(productId);
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        product.setRating(avg);
        product.setReviewCount(reviews.size());
        productRepository.save(product);
        return saved;
    }

    public List<Review> getReviewsForProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}

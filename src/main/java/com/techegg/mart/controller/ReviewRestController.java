package com.techegg.mart.controller;

import com.techegg.mart.entity.Review;
import com.techegg.mart.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    private final ReviewRepository reviewRepository;

    public ReviewRestController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
        // Ensure createdAt is set if not provided
        if (review.getCreatedAt() == null) {
            // Review entity constructor already sets createdAt, but just in case
        }
        Review saved = reviewRepository.save(review);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @Valid @RequestBody Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        // Update mutable fields
        review.setProduct(reviewDetails.getProduct());
        review.setUserId(reviewDetails.getUserId());
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        // createdAt typically not updated
        return reviewRepository.save(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        reviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

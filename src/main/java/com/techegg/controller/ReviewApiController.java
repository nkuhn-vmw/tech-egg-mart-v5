package com.techegg.controller;

import com.techegg.dto.ReviewRequest;
import com.techegg.domain.Review;
import com.techegg.service.ReviewService;
import com.techegg.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewApiController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewApiController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/product/{productId}")
    public List<Review> getByProduct(@PathVariable Long productId) {
        // Returns list of reviews; if product not found, service throws ResourceNotFoundException
        return reviewService.findByProductId(productId);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Review> addReview(@PathVariable Long productId,
                                            @Valid @RequestBody ReviewRequest request) {
        Review review = new Review();
        review.setReviewerName(request.getReviewerName());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setDate(Instant.now());
        Review saved = reviewService.addReview(productId, review);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

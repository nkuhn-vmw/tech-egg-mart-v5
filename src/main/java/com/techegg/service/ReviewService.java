package com.techegg.service;

import com.techegg.model.Review;
import com.techegg.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public Review createReview(Review review) {
        review.setId(null);
        return reviewRepository.save(review);
    }

    @Transactional
    public Optional<Review> updateReview(Long id, Review updated) {
        return reviewRepository.findById(id).map(existing -> {
            existing.setReviewerName(updated.getReviewerName());
            existing.setRating(updated.getRating());
            existing.setComment(updated.getComment());
            existing.setProduct(updated.getProduct());
            return reviewRepository.save(existing);
        });
    }

    @Transactional
    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

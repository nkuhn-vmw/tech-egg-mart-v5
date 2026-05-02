package com.techegg.mart.service;

import com.techegg.mart.dto.ReviewRequest;
import com.techegg.mart.dto.ReviewResponse;
import com.techegg.mart.exception.ResourceNotFoundException;
import com.techegg.mart.model.Review;
import com.techegg.mart.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return convertToResponse(review);
    }

    public ReviewResponse createReview(ReviewRequest request) {
        Review review = convertToEntity(request);
        Review savedReview = reviewRepository.save(review);
        return convertToResponse(savedReview);
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        existingReview.setUserId(request.getUserId());
        existingReview.setRating(request.getRating());
        existingReview.setComment(request.getComment());
        
        Review updatedReview = reviewRepository.save(existingReview);
        return convertToResponse(updatedReview);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        reviewRepository.delete(review);
    }

    private ReviewResponse convertToResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUserId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }

    private Review convertToEntity(ReviewRequest request) {
        Review review = new Review();
        review.setUserId(request.getUserId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return review;
    }
}
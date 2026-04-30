package com.techegg.dto;

import java.time.Instant;

import com.techegg.domain.Review;

public class ReviewResponse {
    private Long id;
    private String reviewerName;
    private Integer rating;
    private String comment;
    private Instant date;

    public static ReviewResponse fromEntity(Review review) {
        ReviewResponse resp = new ReviewResponse();
        resp.id = review.getId();
        resp.reviewerName = review.getReviewerName();
        resp.rating = review.getRating();
        resp.comment = review.getComment();
        resp.date = review.getDate();
        return resp;
    }

    // Getters
    public Long getId() { return id; }
    public String getReviewerName() { return reviewerName; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public Instant getDate() { return date; }
}

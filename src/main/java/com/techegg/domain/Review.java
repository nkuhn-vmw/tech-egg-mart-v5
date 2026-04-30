package com.techegg.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @jakarta.validation.constraints.NotBlank
    private String reviewerName;
    @jakarta.validation.constraints.NotNull
    @jakarta.validation.constraints.Min(1)
    @jakarta.validation.constraints.Max(5)
    private Integer rating; // 1-5
    @Lob
    @jakarta.validation.constraints.NotBlank
    private String comment;
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private com.techegg.domain.User user;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Instant getDate() { return date; }
    public void setDate(Instant date) { this.date = date; }

    public com.techegg.domain.User getUser() { return user; }
    public void setUser(com.techegg.domain.User user) { this.user = user; }
}

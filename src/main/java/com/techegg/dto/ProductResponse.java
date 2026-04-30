package com.techegg.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.techegg.domain.Product;
import com.techegg.domain.Review;

public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String brand;
    private String shortDescription;
    private String longDescription;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String imageUrl;
    private String thumbnailUrls;
    private Long categoryId;
    private Integer stock;
    private Boolean freeShipping;
    private Boolean featured;
    private Double rating;
    private Integer reviewCount;
    private Instant createdAt;
    private List<ReviewResponse> reviews;

    // Convert entity to response DTO
    public static ProductResponse fromEntity(Product product) {
        ProductResponse resp = new ProductResponse();
        resp.id = product.getId();
        resp.sku = product.getSku();
        resp.name = product.getName();
        resp.brand = product.getBrand();
        resp.shortDescription = product.getShortDescription();
        resp.longDescription = product.getLongDescription();
        resp.price = product.getPrice();
        resp.originalPrice = product.getOriginalPrice();
        resp.imageUrl = product.getImageUrl();
        resp.thumbnailUrls = product.getThumbnailUrls();
        resp.categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        resp.stock = product.getStock();
        resp.freeShipping = product.getFreeShipping();
        resp.featured = product.getFeatured();
        resp.rating = product.getRating();
        resp.reviewCount = product.getReviewCount();
        resp.createdAt = product.getCreatedAt();
        if (product.getReviews() != null) {
            resp.reviews = product.getReviews().stream()
                    .map(ReviewResponse::fromEntity)
                    .collect(Collectors.toList());
        }
        return resp;
    }

    // Getters (setters omitted for brevity as this is a read‑only response)
    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public String getImageUrl() { return imageUrl; }
    public String getThumbnailUrls() { return thumbnailUrls; }
    public Long getCategoryId() { return categoryId; }
    public Integer getStock() { return stock; }
    public Boolean getFreeShipping() { return freeShipping; }
    public Boolean getFeatured() { return featured; }
    public Double getRating() { return rating; }
    public Integer getReviewCount() { return reviewCount; }
    public Instant getCreatedAt() { return createdAt; }
    public List<ReviewResponse> getReviews() { return reviews; }
}

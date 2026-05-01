package com.techegg.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a product in the catalogue.
 * Includes fields for brand, model, specifications, pricing, image URL, inventory and
 * relationships to Category and Review.
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(length = 2000)
    private String description;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    /** Free‑form specifications – could be JSON, XML or a simple string. */
    private String specifications;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    /** URL to the main product image */
    private String imageUrl;

    /** Number of items in stock */
    private Integer inventory;

    // ---------------------------------------------------------------------
    // Relationships
    // ---------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------
    public Product() {
        // Default constructor for JPA
    }

    public Product(String name, String description, String brand, String model,
                   String specifications, BigDecimal price, String imageUrl,
                   Integer inventory, Category category) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.model = model;
        this.specifications = specifications;
        this.price = price;
        this.imageUrl = imageUrl;
        this.inventory = inventory;
        this.category = category;
    }

    // ---------------------------------------------------------------------
    // Getters and Setters
    // ---------------------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Integer getInventory() { return inventory; }
    public void setInventory(Integer inventory) { this.inventory = inventory; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    /** Helper to add a review and keep both sides of the relationship in sync. */
    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    /** Helper to remove a review and keep both sides of the relationship in sync. */
    public void removeReview(Review review) {
        reviews.remove(review);
        review.setProduct(null);
    }
}

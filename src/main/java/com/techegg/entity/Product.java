package com.techegg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * JPA entity representing a product in the catalog.
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Product description is required")
    @Column(nullable = false, length = 2000)
    private String description;

    @NotNull(message = "Product price is required")
    @Positive(message = "Price must be greater than zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    @NotBlank(message = "Image URL is required")
    @Column(nullable = false)
    private String imageUrl;

    @NotBlank(message = "Brand is required")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "Specifications are required")
    @Column(nullable = false, length = 2000)
    private String specifications;

    @PositiveOrZero(message = "Rating cannot be negative")
    @Column(nullable = false)
    private Double rating; // rating from 0 to 5

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    @Column(nullable = false)
    private Integer stockQuantity;

    // Default constructor required by JPA
    public Product() {}

    public Product(String name, String description, BigDecimal price, String category, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}

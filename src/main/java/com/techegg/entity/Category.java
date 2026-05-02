package com.techegg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * JPA entity representing a product category.
 * Supports hierarchical categories via a self‑referencing parentCategory field.
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name must be less than 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Category description must be less than 500 characters")
    @Column(length = 500)
    private String description;

    // Self‑referencing many‑to‑one relationship for hierarchical categories
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    public Category() {}

    public Category(String name, String description, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}

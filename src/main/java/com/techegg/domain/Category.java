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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a product category.
 * <p>
 * Each category can have a parent category, forming a hierarchy. The parent
 * relationship is optional to allow top‑level categories. Child categories are
 * mapped with a {@code OneToMany} relationship for convenient navigation.
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Category name must not be blank")
    @Size(max = 100, message = "Category name must be at most 100 characters")
    private String name;

    @Column(length = 500)
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    /**
     * Parent category – nullable for root categories.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    /**
     * Child categories – cascade delete so that removing a category also removes
     * its sub‑categories. Orphan removal ensures detached children are deleted.
     */
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> childCategories = new ArrayList<>();

    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------
    public Category() {
    }

    public Category(String name, String description, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
    }

    // ---------------------------------------------------------------------
    // Getters and Setters
    // ---------------------------------------------------------------------
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

    public List<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    /**
     * Helper to add a child category while maintaining both sides of the
     * relationship.
     */
    public void addChildCategory(Category child) {
        child.setParentCategory(this);
        this.childCategories.add(child);
    }

    /**
     * Helper to remove a child category.
     */
    public void removeChildCategory(Category child) {
        child.setParentCategory(null);
        this.childCategories.remove(child);
    }
}

package com.techegg.mart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 200, message = "Category name must be less than 200 characters")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    // optional parent category id for hierarchical categories
    private Long parentCategoryId;

    public CategoryRequest() {}

    public CategoryRequest(String name, String description, Long parentCategoryId) {
        this.name = name;
        this.description = description;
        this.parentCategoryId = parentCategoryId;
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

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}

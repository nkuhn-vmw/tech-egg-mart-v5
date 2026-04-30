package com.techegg.controller;

import com.techegg.domain.Category;
import com.techegg.domain.Product;
import com.techegg.service.CategoryService;
import com.techegg.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    /**
     * Display products for a given category slug with optional price filters.
     */
    @GetMapping("/categories/{slug}")
    public String viewCategory(
            @PathVariable String slug,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            Model model) {
        Category category = categoryService.findBySlug(slug);
        if (category == null) {
            throw new com.techegg.exception.ResourceNotFoundException("Category not found");
        }
        // Retrieve products filtered by category and optional price range
        List<Product> products = productService.findFiltered(category.getId(), minPrice, maxPrice);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", category.getId());
        model.addAttribute("selectedCategoryName", category.getName());
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "product-list"; // reuse the same product list template
    }
}

package com.techegg.controller;

import com.techegg.domain.Product;
import com.techegg.domain.Category;
import com.techegg.domain.Review;
import com.techegg.service.CategoryService;
import com.techegg.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            Model model) {
        List<Product> products = productService.findFiltered(categoryId, minPrice, maxPrice);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "product-list";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        List<Review> reviews = productService.getReviewsForProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "product-detail";
    }

    @PostMapping("/products/{id}/reviews")
    public String addReview(@PathVariable("id") Long productId,
                            @RequestParam("reviewerName") @NotBlank String reviewerName,
                            @RequestParam("rating") @NotNull @Min(1) @Max(5) Integer rating,
                            @RequestParam("comment") @NotBlank String comment,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please fill all required fields correctly");
            return "redirect:/products/" + productId;
        }
        
        Review review = new Review();
        review.setReviewerName(reviewerName);
        review.setRating(rating);
        review.setComment(comment);
        review.setDate(Instant.now());
        productService.addReview(productId, review);
        redirectAttributes.addFlashAttribute("message", "Review added successfully");
        return "redirect:/products/" + productId;
    }
}

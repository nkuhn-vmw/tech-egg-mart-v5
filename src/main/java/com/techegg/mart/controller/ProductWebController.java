package com.techegg.mart.controller;

import com.techegg.mart.entity.Product;
import com.techegg.mart.entity.Review;
import com.techegg.mart.repository.ProductRepository;
import com.techegg.mart.repository.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductWebController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductWebController(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/products/{id}/detail")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "error/404"; // you may have a 404 template
        }
        List<Review> reviews = reviewRepository.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "product-detail"; // resolves to src/main/resources/templates/product-detail.html
    }
}

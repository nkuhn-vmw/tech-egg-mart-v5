package com.techegg.controller;

import com.techegg.domain.Product;
import com.techegg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Display the detail page for a product.
     * URL: /products/{id}
     */
    @GetMapping("/products/{id}")
    public String getProductDetail(@PathVariable("id") Long id, Model model) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            // Return a simple error view or 404 page
            return "error/404";
        }
        Product product = optionalProduct.get();
        model.addAttribute("product", product);
        // The product entity already contains the list of reviews
        model.addAttribute("reviews", product.getReviews());
        model.addAttribute("newReview", new com.techegg.dto.ReviewForm());
        return "product-detail";
    }
}

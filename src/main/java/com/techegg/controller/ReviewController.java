package com.techegg.controller;

import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.dto.ReviewForm;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public String addReview(@Valid @ModelAttribute("newReview") ReviewForm form,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            // Reload product detail with errors
            Optional<Product> productOpt = productRepository.findById(form.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                model.addAttribute("product", product);
                model.addAttribute("reviews", product.getReviews());
                model.addAttribute("newReview", form);
                return "product-detail";
            } else {
                return "error/404";
            }
        }
        // Find product
        Optional<Product> productOpt = productRepository.findById(form.getProductId());
        if (productOpt.isEmpty()) {
            return "error/404";
        }
        Product product = productOpt.get();
        Review review = new Review();
        review.setReviewerName(form.getReviewerName());
        review.setRating(form.getRating());
        review.setComment(form.getComment());
        review.setProduct(product);
        // Save review
        reviewRepository.save(review);
        // Ensure product's review list is updated
        product.addReview(review);
        productRepository.save(product);
        // Redirect to product detail page
        return "redirect:/products/" + product.getId();
    }
}

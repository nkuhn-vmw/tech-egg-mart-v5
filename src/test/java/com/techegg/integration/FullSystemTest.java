package com.techegg.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techegg.domain.Category;
import com.techegg.domain.Product;
import com.techegg.domain.Review;
import com.techegg.domain.User;
import com.techegg.repository.CategoryRepository;
import com.techegg.repository.ProductRepository;
import com.techegg.repository.ReviewRepository;
import com.techegg.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FullSystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Category category;
    private Product product;
    private User user;
    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        // Clean repositories
        reviewRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();

        // Create a category
        category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic gadgets");
        category.setSlug("electronics");
        category = categoryRepository.save(category);

        // Create a product
        product = new Product();
        product.setName("Smartphone");
        product.setDescription("A cool smartphone");
        product.setPrice(BigDecimal.valueOf(199.99));
        product.setImageUrl("http://example.com/img.png");
        product.setCategory(category);
        product = productRepository.save(product);

        // Create a user for authentication
        user = new User();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singleton("ROLE_USER"));
        user = userRepository.save(user);

        // Obtain JWT token via login endpoint
        String loginJson = "{\"username\":\"testuser\",\"password\":\"password\"}";
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        // response is JSON like {"token":"..."}
        jwtToken = new ObjectMapper().readTree(response).get("token").asText();
    }

    @Test
    void testProductListingAndDetail() throws Exception {
        // List products
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(view().name("product-list"));

        // Product detail
        mockMvc.perform(get("/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("reviews"))
                .andExpect(view().name("product-detail"));
    }

    @Test
    void testAddReviewWithAuthentication() throws Exception {
        // Prepare review JSON (the controller expects form fields, but we can simulate form submit)
        mockMvc.perform(post("/products/" + product.getId() + "/reviews")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("reviewerName", "Alice")
                .param("rating", "5")
                .param("comment", "Excellent!")
                .header("Authorization", "Bearer " + jwtToken)
                .sessionAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/" + product.getId()));

        // Verify review persisted
        Review saved = reviewRepository.findByProductId(product.getId()).get(0);
        assertThat(saved.getReviewerName()).isEqualTo("Alice");
        assertThat(saved.getRating()).isEqualTo(5);
        assertThat(saved.getComment()).isEqualTo("Excellent!");
    }
}

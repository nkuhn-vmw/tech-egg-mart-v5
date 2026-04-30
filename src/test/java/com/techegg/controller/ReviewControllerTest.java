package com.techegg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techegg.domain.Review;
import com.techegg.dto.ReviewRequest;
import com.techegg.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        review1 = new Review();
        review1.setId(1L);
        review1.setReviewerName("John Doe");
        review1.setRating(5);
        review1.setComment("Great product!");
        review1.setDate(Instant.now());

        review2 = new Review();
        review2.setId(2L);
        review2.setReviewerName("Jane Smith");
        review2.setRating(4);
        review2.setComment("Good product!");
        review2.setDate(Instant.now());
    }

    @Test
    void testGetAllReviews() throws Exception {
        when(reviewService.findAll()).thenReturn(Arrays.asList(review1, review2));

        mockMvc.perform(get("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetReviewById_Found() throws Exception {
        when(reviewService.findById(1L)).thenReturn(Optional.of(review1));

        mockMvc.perform(get("/api/reviews/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.reviewerName").value("John Doe"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void testGetReviewById_NotFound() throws Exception {
        when(reviewService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reviews/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateReview_Valid() throws Exception {
        when(reviewService.save(any(Review.class))).thenReturn(review1);

        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(review1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testAddReviewToProduct_Valid() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest("John Doe", 5, "Great product!");
        when(reviewService.addReview(anyLong(), any(Review.class))).thenReturn(review1);

        mockMvc.perform(post("/api/reviews/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetReviewsByProductId() throws Exception {
        when(reviewService.findByProductId(1L)).thenReturn(Arrays.asList(review1, review2));

        mockMvc.perform(get("/api/reviews/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
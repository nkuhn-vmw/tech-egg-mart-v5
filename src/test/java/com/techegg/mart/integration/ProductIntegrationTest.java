package com.techegg.mart.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techegg.mart.dto.ProductRequest;
import com.techegg.mart.dto.ProductResponse;
import com.techegg.mart.entity.Category;
import com.techegg.mart.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category savedCategory;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic devices");
        savedCategory = categoryRepository.save(category);
    }

    @Test
    void testCreateReadUpdateDeleteProduct() throws Exception {
        // Create product request
        ProductRequest request = new ProductRequest();
        request.setName("Test Phone");
        request.setDescription("A smartphone");
        request.setPrice(new java.math.BigDecimal("199.99"));
        request.setImageUrl("http://example.com/image.jpg");
        request.setCategoryId(savedCategory.getId());
        request.setBrand("TestBrand");
        request.setModel("X1");
        request.setSpecifications("Spec details");
        request.setStockQuantity(10);
        request.setRating(4.5);

        String json = objectMapper.writeValueAsString(request);

        // POST /products
        String responseJson = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductResponse created = objectMapper.readValue(responseJson, ProductResponse.class);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Test Phone");

        // GET /products/{id}
        mockMvc.perform(get("/products/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Phone"));

        // Update product
        request.setName("Updated Phone");
        String updateJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/products/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Phone"));

        // Delete product
        mockMvc.perform(delete("/products/{id}", created.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/products/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }
}

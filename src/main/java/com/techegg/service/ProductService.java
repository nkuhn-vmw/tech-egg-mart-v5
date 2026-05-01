package com.techegg.service;

import com.techegg.domain.Product;
import com.techegg.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Create a new product.
     */
    @Transactional
    public Product createProduct(Product product) {
        // Additional business logic can be added here (e.g., checking duplicates)
        return productRepository.save(product);
    }

    /**
     * Retrieve a product by its ID.
     */
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Retrieve all products.
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Update an existing product.
     */
    @Transactional
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existing -> {
            // Update fields – only non-null values are applied to avoid overwriting unintentionally
            if (updatedProduct.getName() != null) existing.setName(updatedProduct.getName());
            if (updatedProduct.getDescription() != null) existing.setDescription(updatedProduct.getDescription());
            if (updatedProduct.getBrand() != null) existing.setBrand(updatedProduct.getBrand());
            if (updatedProduct.getModel() != null) existing.setModel(updatedProduct.getModel());
            if (updatedProduct.getSpecifications() != null) existing.setSpecifications(updatedProduct.getSpecifications());
            if (updatedProduct.getPrice() != null) existing.setPrice(updatedProduct.getPrice());
            if (updatedProduct.getImageUrl() != null) existing.setImageUrl(updatedProduct.getImageUrl());
            if (updatedProduct.getStockQuantity() != null) existing.setStockQuantity(updatedProduct.getStockQuantity());
            if (updatedProduct.getCategory() != null) existing.setCategory(updatedProduct.getCategory());
            // Reviews are managed through ReviewService; we don't replace the whole list here.
            return productRepository.save(existing);
        });
    }

    /**
     * Delete a product by its ID.
     */
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

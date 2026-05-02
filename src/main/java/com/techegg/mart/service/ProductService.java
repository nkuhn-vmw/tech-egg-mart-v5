package com.techegg.mart.service;

import com.techegg.mart.entity.Product;
import com.techegg.mart.repository.ProductRepository;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setImageUrl(productDetails.getImageUrl());
            product.setCategoryId(productDetails.getCategoryId());
            product.setBrand(productDetails.getBrand());
            product.setModel(productDetails.getModel());
            product.setSpecifications(productDetails.getSpecifications());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setRating(productDetails.getRating());
            return productRepository.save(product);
        });
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}

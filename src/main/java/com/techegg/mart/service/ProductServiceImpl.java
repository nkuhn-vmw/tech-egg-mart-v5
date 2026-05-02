package com.techegg.mart.service;

import com.techegg.mart.dto.ProductRequest;
import com.techegg.mart.dto.ProductResponse;
import com.techegg.mart.exception.ResourceNotFoundException;
import com.techegg.mart.model.Product;
import com.techegg.mart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = convertToEntity(request);
        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setImageUrl(request.getImageUrl());
        existingProduct.setCategoryId(request.getCategoryId());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setModel(request.getModel());
        existingProduct.setSpecifications(request.getSpecifications());
        existingProduct.setStockQuantity(request.getStockQuantity());
        existingProduct.setRating(request.getRating());
        
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    private ProductResponse convertToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategoryId(),
                product.getBrand(),
                product.getModel(),
                product.getSpecifications(),
                product.getStockQuantity(),
                product.getRating(),
                product.getCreatedAt()
        );
    }

    private Product convertToEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategoryId(request.getCategoryId());
        product.setBrand(request.getBrand());
        product.setModel(request.getModel());
        product.setSpecifications(request.getSpecifications());
        product.setStockQuantity(request.getStockQuantity());
        product.setRating(request.getRating());
        return product;
    }
}
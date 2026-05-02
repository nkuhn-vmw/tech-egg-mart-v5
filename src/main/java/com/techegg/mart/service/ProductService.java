package com.techegg.mart.service;

import com.techegg.mart.dto.ProductRequest;
import com.techegg.mart.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    
    List<ProductResponse> getAllProducts();
    
    ProductResponse getProductById(Long id);
    
    ProductResponse createProduct(ProductRequest request);
    
    ProductResponse updateProduct(Long id, ProductRequest request);
    
    void deleteProduct(Long id);
}
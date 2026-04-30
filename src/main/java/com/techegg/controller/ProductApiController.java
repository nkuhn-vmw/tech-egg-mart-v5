package com.techegg.controller;

import com.techegg.dto.ProductRequest;
import com.techegg.dto.ProductResponse;
import com.techegg.domain.Product;
import com.techegg.service.ProductService;
import com.techegg.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.findAll()
                .stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductResponse.fromEntity(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product product = new Product();
        // map fields from request to entity
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setShortDescription(request.getShortDescription());
        product.setLongDescription(request.getLongDescription());
        product.setPrice(request.getPrice());
        product.setOriginalPrice(request.getOriginalPrice());
        product.setImageUrl(request.getImageUrl());
        product.setThumbnailUrls(request.getThumbnailUrls());
        product.setStock(request.getStock());
        product.setFreeShipping(request.getFreeShipping());
        product.setFeatured(request.getFeatured());
        // category association handled if categoryId provided
        if (request.getCategoryId() != null) {
            // lazy load category via service (could be improved)
            // For simplicity, we just set a reference with id
            com.techegg.domain.Category category = new com.techegg.domain.Category();
            category.setId(request.getCategoryId());
            product.setCategory(category);
        }
        Product saved = productService.save(product);
        return new ResponseEntity<>(ProductResponse.fromEntity(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        // update fields
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setShortDescription(request.getShortDescription());
        product.setLongDescription(request.getLongDescription());
        product.setPrice(request.getPrice());
        product.setOriginalPrice(request.getOriginalPrice());
        product.setImageUrl(request.getImageUrl());
        product.setThumbnailUrls(request.getThumbnailUrls());
        product.setStock(request.getStock());
        product.setFreeShipping(request.getFreeShipping());
        product.setFeatured(request.getFeatured());
        if (request.getCategoryId() != null) {
            com.techegg.domain.Category category = new com.techegg.domain.Category();
            category.setId(request.getCategoryId());
            product.setCategory(category);
        }
        Product saved = productService.save(product);
        return ProductResponse.fromEntity(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

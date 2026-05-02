package com.techegg.controller;

import com.techegg.dto.ProductRequest;
import com.techegg.dto.ProductResponse;
import com.techegg.entity.Product;
import com.techegg.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private final ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(p -> ResponseEntity.ok(toResponse(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = fromRequest(request);
        product = productRepository.save(product);
        return new ResponseEntity<>(toResponse(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody ProductRequest request) {
        return productRepository.findById(id)
                .map(existing -> {
                    // update fields
                    existing.setName(request.getName());
                    existing.setDescription(request.getDescription());
                    existing.setPrice(request.getPrice());
                    existing.setCategory(request.getCategory());
                    existing.setImageUrl(request.getImageUrl());
                    existing.setBrand(request.getBrand());
                    existing.setModel(request.getModel());
                    existing.setSpecifications(request.getSpecifications());
                    existing.setStockQuantity(request.getStockQuantity());
                    // rating could be computed elsewhere; keep existing
                    productRepository.save(existing);
                    return ResponseEntity.ok(toResponse(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Helper methods to convert between entity and DTOs
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getImageUrl(),
                product.getBrand(),
                product.getModel(),
                product.getSpecifications(),
                product.getRating(),
                product.getStockQuantity()
        );
    }

    private Product fromRequest(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());
        product.setModel(request.getModel());
        product.setSpecifications(request.getSpecifications());
        product.setStockQuantity(request.getStockQuantity());
        // rating default to 0.0
        product.setRating(0.0);
        return product;
    }
}

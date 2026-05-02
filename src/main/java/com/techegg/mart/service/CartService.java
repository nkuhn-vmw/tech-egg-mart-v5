package com.techegg.mart.service;

import com.techegg.mart.exception.ResourceNotFoundException;
import com.techegg.mart.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Session scoped service that holds a simple shopping cart.
 * The cart is stored as a map of productId -> quantity in the HTTP session.
 */
@Component
@SessionScope
public class CartService {

    /** Map of product id to quantity */
    private final Map<Long, Integer> items = new HashMap<>();

    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /** Add a product with the given quantity to the cart. */
    public void addProduct(Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        // Ensure product exists
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        items.merge(productId, quantity, Integer::sum);
    }

    /** Update quantity of an existing product in the cart. */
    public void updateProduct(Long productId, int quantity) {
        if (!items.containsKey(productId)) {
            throw new ResourceNotFoundException("Product not in cart");
        }
        if (quantity <= 0) {
            items.remove(productId);
        } else {
            items.put(productId, quantity);
        }
    }

    /** Remove a product from the cart. */
    public void removeProduct(Long productId) {
        if (!items.containsKey(productId)) {
            throw new ResourceNotFoundException("Product not in cart");
        }
        items.remove(productId);
    }

    /** Get an immutable view of the cart items. */
    public Map<Long, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /** Clear the cart – typically after successful checkout. */
    public void clear() {
        items.clear();
    }
}

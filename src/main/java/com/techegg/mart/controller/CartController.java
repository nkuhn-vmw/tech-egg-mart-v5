package com.techegg.mart.controller;

import com.techegg.mart.dto.CartItemRequest;
import com.techegg.mart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /** Retrieve current cart items */
    @GetMapping
    public ResponseEntity<Map<Long, Integer>> getCart() {
        return ResponseEntity.ok(cartService.getItems());
    }

    /** Add a product to the cart */
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody CartItemRequest request) {
        cartService.addProduct(request.getProductId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** Update quantity of a product in the cart */
    @PutMapping("/update")
    public ResponseEntity<Void> updateCart(@RequestBody CartItemRequest request) {
        cartService.updateProduct(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    /** Remove a product from the cart */
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long productId) {
        cartService.removeProduct(productId);
        return ResponseEntity.noContent().build();
    }

    /** Simple checkout endpoint that clears the cart */
    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout() {
        // In a real application, you would process payment, create order, etc.
        cartService.clear();
        return ResponseEntity.ok().build();
    }
}

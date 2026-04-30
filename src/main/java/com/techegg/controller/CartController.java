package com.techegg.controller;

import com.techegg.cart.Cart;
import com.techegg.cart.CartItem;
import com.techegg.domain.Product;
import com.techegg.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Controller for shopping cart operations.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    private final Cart cart;
    private final ProductService productService;

    @Autowired
    public CartController(Cart cart, ProductService productService) {
        this.cart = cart;
        this.productService = productService;
    }

    /**
     * View cart contents.
     */
    @GetMapping
    public String viewCart(Model model) {
        Collection<CartItem> items = cart.getItems();
        model.addAttribute("items", items);
        model.addAttribute("totalAmount", cart.getTotalAmount());
        return "cart";
    }

    /**
     * Add a product to the cart.
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity) {
        Product product = productService.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        cart.addProduct(product, quantity);
        return "redirect:/cart";
    }

    /**
     * Remove a product from the cart.
     */
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId) {
        cart.removeProduct(productId);
        return "redirect:/cart";
    }

    /**
     * Update quantity of a product in the cart.
     */
    @PostMapping("/update")
    public String updateQuantity(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity) {
        cart.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    /**
     * Simple checkout – clears the cart and shows a confirmation page.
     */
    @PostMapping("/checkout")
    public String checkout(Model model) {
        double total = cart.getTotalAmount();
        cart.clear();
        model.addAttribute("total", total);
        return "checkout-success";
    }
}

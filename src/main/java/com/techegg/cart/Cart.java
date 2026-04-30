package com.techegg.cart;

import com.techegg.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Session-scoped shopping cart.
 */
@SessionScope
@Component
public class Cart {
    /**
     * Key: product id, Value: CartItem
     */
    private final Map<Long, CartItem> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (product == null || product.getId() == null) {
            return;
        }
        CartItem existing = items.get(product.getId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            items.put(product.getId(), new CartItem(product, quantity));
        }
    }

    public void removeProduct(Long productId) {
        if (productId != null) {
            items.remove(productId);
        }
    }

    public void updateQuantity(Long productId, int quantity) {
        if (productId == null) {
            return;
        }
        CartItem item = items.get(productId);
        if (item != null) {
            if (quantity <= 0) {
                items.remove(productId);
            } else {
                item.setQuantity(quantity);
            }
        }
    }

    public Collection<CartItem> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public double getTotalAmount() {
        return items.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public void clear() {
        items.clear();
    }
}

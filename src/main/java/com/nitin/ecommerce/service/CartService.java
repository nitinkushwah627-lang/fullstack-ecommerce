package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.Cart;
import java.util.List;

// Only declare methods here
// Actual implementation in CartServiceImpl
public interface CartService {

    // =========================
    // Add To Cart
    // =========================
    Cart addToCart(Cart cart);

    // =========================
    // Get User Cart
    // =========================
    List<Cart> getCartByUserId(Long userId);

    // =========================
    // Remove Cart Item
    // =========================
    void removeFromCart(Long id);

    // =========================
    // Clear Cart
    // =========================
    void clearCart(Long userId);
}
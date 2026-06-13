package com.nitin.ecommerce.controller;

import com.nitin.ecommerce.model.Cart;
import com.nitin.ecommerce.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This class handles Cart APIs
@RestController
@RequestMapping("/api/cart")
public class CartController {

    // Injecting service
    @Autowired
    private CartService cartService;

    // =========================
    // ADD TO CART
    // POST /api/cart
    // =========================
    @PostMapping
    public Cart addToCart(@RequestBody Cart cart) {

        // Add product to cart
        return cartService.addToCart(cart);
    }

    // =========================
    // GET USER CART
    // GET /api/cart/{userId}
    // =========================
    @GetMapping("/{userId}")
    public List<Cart> getCartByUserId(@PathVariable Long userId) {

        // Get user's cart
        return cartService.getCartByUserId(userId);
    }

    // =========================
    // REMOVE FROM CART
    // DELETE /api/cart/{id}
    // =========================
    @DeleteMapping("/{id}")
    public String removeFromCart(@PathVariable Long id) {

        // Delete cart item
        cartService.removeFromCart(id);

        return "Item removed from cart!";
    }

    // =========================
    // CLEAR CART
    // DELETE /api/cart/clear/{userId}
    // =========================
    @DeleteMapping("/clear/{userId}")
    public String clearCart(@PathVariable Long userId) {

        // Clear user's entire cart
        cartService.clearCart(userId);

        return "Cart cleared successfully!";
    }
}
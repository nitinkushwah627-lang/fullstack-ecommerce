package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.Cart;
import com.nitin.ecommerce.repository.CartRepository;
import com.nitin.ecommerce.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    // Injecting repository
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // Helper method to enrich cart item with product details
    private void enrichCartItem(Cart item) {
        if (item.getProductId() != null) {
            productRepository.findById(item.getProductId()).ifPresent(product -> {
                item.setProductName(product.getName());
                item.setProductPrice(product.getPrice());
                item.setProductImageUrl(product.getImageUrl());
                item.setProductDescription(product.getDescription());
            });
        }
    }

    // =========================
    // ADD TO CART
    // =========================
    @Override
    public Cart addToCart(Cart cart) {
        // Check if product is already in user's cart
        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductId(cart.getUserId(), cart.getProductId());

        Cart savedItem;
        if (existingCartItem.isPresent()) {
            Cart item = existingCartItem.get();
            // Update the quantity of the existing cart item
            item.setQuantity(item.getQuantity() + cart.getQuantity());
            savedItem = cartRepository.save(item);
        } else {
            // Save cart item
            savedItem = cartRepository.save(cart);
        }
        enrichCartItem(savedItem);
        return savedItem;
    }

    // =========================
    // GET USER CART
    // =========================
    @Override
    public List<Cart> getCartByUserId(Long userId) {

        // Get all user's cart items
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        cartItems.forEach(this::enrichCartItem);
        return cartItems;
    }

    // =========================
    // REMOVE FROM CART
    // =========================
    @Override
    public void removeFromCart(Long id) {

        // Delete cart item by ID
        cartRepository.deleteById(id);
    }

    // =========================
    // CLEAR CART
    // =========================
    @Override
    public void clearCart(Long userId) {

        // Get all user's cart items
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        // Delete each item
        cartRepository.deleteAll(cartItems);
    }
}
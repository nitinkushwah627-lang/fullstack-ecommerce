package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.Cart;
import com.nitin.ecommerce.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    public void setUp() {
        cartRepository.deleteAll();
    }

    @Test
    public void testAddToCart_updatesQuantityForExistingProduct() {
        // Create first cart item
        Cart item1 = new Cart();
        item1.setUserId(1L);
        item1.setProductId(100L);
        item1.setQuantity(2);

        // Add to cart
        cartService.addToCart(item1);

        // Create second cart item for same product
        Cart item2 = new Cart();
        item2.setUserId(1L);
        item2.setProductId(100L);
        item2.setQuantity(3);

        // Add to cart
        cartService.addToCart(item2);

        // Retrieve cart items
        List<Cart> cartItems = cartService.getCartByUserId(1L);

        // Assertions
        assertEquals(1, cartItems.size(), "Should only have one entry for the product in the cart");
        assertEquals(5, cartItems.get(0).getQuantity(), "Quantity should be the sum of both additions");
    }
}

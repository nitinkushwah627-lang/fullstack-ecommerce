package com.nitin.ecommerce.controller;

import com.nitin.ecommerce.model.Order;
import com.nitin.ecommerce.model.OrderItem;
import com.nitin.ecommerce.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This class handles Order APIs
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    // Inject service
    @Autowired
    private OrderService orderService;

    // ===================================
    // PLACE ORDER
    // POST /api/orders/place/{userId}
    // Create order from cart
    // ===================================
    @PostMapping("/place/{userId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long userId) {

        // Create order from cart
        Order order = orderService.placeOrder(userId);

        return ResponseEntity.ok(order);
    }

    // ===================================
    // GET MY ORDERS
    // GET /api/orders/user/{userId}
    // Get all user's orders
    // ===================================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {

        // Fetch user's orders
        List<Order> orders = orderService.getOrdersByUserId(userId);

        return ResponseEntity.ok(orders);
    }

    // ===================================
    // GET ORDER ITEMS
    // GET /api/orders/{orderId}/items
    // View order items
    // ===================================
    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getOrderItems(@PathVariable Long orderId) {

        // Fetch order items
        List<OrderItem> items = orderService.getOrderItems(orderId);

        return ResponseEntity.ok(items);
    }

    // ===================================
    // CANCEL ORDER
    // PUT /api/orders/{orderId}/cancel
    // Cancel order
    // ===================================
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {

        // Cancel order
        Order cancelled = orderService.cancelOrder(orderId);

        return ResponseEntity.ok(cancelled);
    }
}

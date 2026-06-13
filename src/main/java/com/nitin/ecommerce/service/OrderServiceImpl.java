package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.Cart;
import com.nitin.ecommerce.model.Order;
import com.nitin.ecommerce.model.OrderItem;
import com.nitin.ecommerce.model.Product;
import com.nitin.ecommerce.repository.CartRepository;
import com.nitin.ecommerce.repository.OrderItemRepository;
import com.nitin.ecommerce.repository.OrderRepository;
import com.nitin.ecommerce.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    // ── DEPENDENCIES ──────────────────────────────────────

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // =========================
    // PLACE ORDER
    // Create order from cart items
    // =========================
    @Override
    public Order placeOrder(Long userId) {

        // Step 1 — Get user's cart
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        // Step 2 — Is cart empty? Throw error
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty! Please add items first.");
        }

        // Step 3 — Calculate total amount (product price * quantity)
        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Cart item : cartItems) {
            // Fetch product's real price
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

            double itemTotal = product.getPrice() * item.getQuantity();
            total += itemTotal;

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice()); // Store real price
            orderItems.add(orderItem);
        }

        // Step 4 — Create new order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        // Step 5 — Save order
        Order savedOrder = orderRepository.save(order);

        // Step 6 — Set orderId in OrderItems and save them
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(savedOrder.getId());
        }
        orderItemRepository.saveAll(orderItems);

        // Step 7 — Clear cart
        cartRepository.deleteAll(cartItems);

        // Step 8 — Return placed order
        return savedOrder;
    }

    // =========================
    // GET ORDERS BY USER ID
    // Get all user's orders
    // =========================
    @Override
    public List<Order> getOrdersByUserId(Long userId) {

        // Fetch user's orders from database
        return orderRepository.findByUserId(userId);
    }

    // Helper method to enrich order item with product details
    private void enrichOrderItem(OrderItem item) {
        if (item.getProductId() != null) {
            productRepository.findById(item.getProductId()).ifPresent(product -> {
                item.setProductName(product.getName());
                item.setProductImageUrl(product.getImageUrl());
            });
        }
    }

    // =========================
    // GET ORDER ITEMS
    // Get order items
    // =========================
    @Override
    public List<OrderItem> getOrderItems(Long orderId) {

        // Does order exist?
        orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Fetch order items
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        items.forEach(this::enrichOrderItem);
        return items;
    }

    // =========================
    // CANCEL ORDER
    // Cancel order
    // =========================
    @Override
    public Order cancelOrder(Long orderId) {

        // Find order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Only PLACED orders can be cancelled
        if (!order.getStatus().equals("PLACED")) {
            throw new RuntimeException("Only PLACED orders can be cancelled. Current status: " + order.getStatus());
        }

        // Update status
        order.setStatus("CANCELLED");

        // Save
        return orderRepository.save(order);
    }
}

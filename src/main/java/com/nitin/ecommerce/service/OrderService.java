package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.Order;
import com.nitin.ecommerce.model.OrderItem;

import java.util.List;

// Sirf methods declare karte hain
// Actual code OrderServiceImpl me hoga
public interface OrderService {

    // =========================
    // Order Place Karo (Cart se)
    // =========================
    Order placeOrder(Long userId);

    // =========================
    // User ke saare Orders Lao
    // =========================
    List<Order> getOrdersByUserId(Long userId);

    // =========================
    // Ek Order ke Items Lao
    // =========================
    List<OrderItem> getOrderItems(Long orderId);

    // =========================
    // Order Cancel Karo
    // =========================
    Order cancelOrder(Long orderId);
}

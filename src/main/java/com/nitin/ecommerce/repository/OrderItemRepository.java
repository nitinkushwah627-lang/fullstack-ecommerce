package com.nitin.ecommerce.repository;

import com.nitin.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// OrderItem table ke liye Repository
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Ek order ke saare items lane ke liye
    List<OrderItem> findByOrderId(Long orderId);
}
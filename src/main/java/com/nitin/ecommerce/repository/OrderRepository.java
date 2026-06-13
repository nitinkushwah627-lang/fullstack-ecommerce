package com.nitin.ecommerce.repository;

import com.nitin.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Order table ke liye Repository
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Ek user ke saare orders lane ke liye
    List<Order> findByUserId(Long userId);
}
package com.nitin.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long productId;

    private int quantity;

    private Double price;

    @Transient
    private String productName;

    @Transient
    private String productImageUrl;
}
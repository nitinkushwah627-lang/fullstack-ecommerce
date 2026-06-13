package com.nitin.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

// Database table hai yeh class
@Data
@Entity
@Table(name = "products") // Table ka naam "products"
public class Product {

    // Primary key — automatic badhegi 1,2,3
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product ka naam
    private String name;

    // Product ki details
    private String description;

    // Product ka price
    private Double price;

    // Product ki image
    private String imageUrl;
}
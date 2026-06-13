package com.nitin.ecommerce.repository;

import com.nitin.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


// Database se baat karne ke liye
// JpaRepository se save, find, delete sab milta hai
public interface ProductRepository
       extends JpaRepository<Product, Long> {
    // Product table ke saath kaam karega
    // Long = ID ka type
}
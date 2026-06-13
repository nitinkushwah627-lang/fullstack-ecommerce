package com.nitin.ecommerce.controller;

import com.nitin.ecommerce.model.Product;
import com.nitin.ecommerce.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This class handles Product APIs
@RestController

// Base URL
@RequestMapping("/api/products")
public class ProductController {

    // Injecting service
    @Autowired
    private ProductService productService;

    // =========================
    // ADD PRODUCT API
    // POST /api/products
    // =========================
    @PostMapping
    public Product addProduct(@RequestBody Product product) {

        // Save product
        return productService.addProduct(product);
    }

    // =========================
    // GET ALL PRODUCTS API
    // GET /api/products
    // =========================
    @GetMapping
    public List<Product> getAllProducts() {

        // Get all products
        return productService.getAllProducts();
    }

    // =========================
    // GET PRODUCT BY ID API
    // GET /api/products/{id}
    // =========================
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {

        // Get product by ID
        return productService.getProductById(id);
    }

    // =========================
    // UPDATE PRODUCT API
    // PUT /api/products/{id}
    // =========================
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        // Update product
        return productService.updateProduct(id, product);
    }

    // =========================
    // DELETE PRODUCT API
    // DELETE /api/products/{id}
    // =========================
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        // Delete product
        productService.deleteProduct(id);

        return "Product Deleted Successfully!";
    }
}
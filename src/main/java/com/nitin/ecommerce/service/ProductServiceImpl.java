package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.Product;
import com.nitin.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // Injecting repository
    @Autowired
    private ProductRepository productRepository;

    // =========================
    // ADD PRODUCT
    // =========================
    @Override
    public Product addProduct(Product product) {

        // Save product to database
        return productRepository.save(product);
    }

    // =========================
    // GET ALL PRODUCTS
    // =========================
    @Override
    public List<Product> getAllProducts() {

        // Get all products from database
        return productRepository.findAll();
    }

    // =========================
    // GET PRODUCT BY ID
    // =========================
    @Override
    public Product getProductById(Long id) {

        // Search product by ID
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // =========================
    // UPDATE PRODUCT
    // =========================
    @Override
    public Product updateProduct(Long id, Product product) {

        // Find old product
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Set new data
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setImageUrl(product.getImageUrl());

        // Save updated product
        return productRepository.save(existing);
    }

    // =========================
    // DELETE PRODUCT
    // =========================
    @Override
    public void deleteProduct(Long id) {

        // Delete product by ID
        productRepository.deleteById(id);
    }
}
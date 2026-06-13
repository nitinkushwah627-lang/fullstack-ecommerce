package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.Product;
import java.util.List;

// Sirf methods declare karte hain
// Actual code ProductServiceImpl me hoga
public interface ProductService {

    // =========================
    // Product Add Karo
    // =========================
    Product addProduct(Product product);

    // =========================
    // Sab Products Lao
    // =========================
    List<Product> getAllProducts();

    // =========================
    // Id Se Ek Product Lao
    // =========================
    Product getProductById(Long id);

    // =========================
    // Product Update Karo
    // =========================
    Product updateProduct(Long id, Product product);

    // =========================
    // Product Delete Karo
    // =========================
    void deleteProduct(Long id);
}
package com.nitin.ecommerce.config;

import com.nitin.ecommerce.model.Product;
import com.nitin.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            Product p1 = new Product();
            p1.setName("Premium Leather Jacket");
            p1.setDescription("Handcrafted genuine leather jacket. Perfect for all seasons with soft inner lining and heavy-duty zippers.");
            p1.setPrice(4999.0);
            p1.setImageUrl("https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500&q=80");

            Product p2 = new Product();
            p2.setName("Wireless Noise-Cancelling Headphones");
            p2.setDescription("Experience pure sound with active noise-cancelling technology, 40-hour battery life, and plush earcups.");
            p2.setPrice(12999.0);
            p2.setImageUrl("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&q=80");

            Product p3 = new Product();
            p3.setName("Minimalist Quartz Watch");
            p3.setDescription("Elegant design featuring a genuine leather strap, scratch-resistant sapphire glass, and Japanese quartz movement.");
            p3.setPrice(3499.0);
            p3.setImageUrl("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&q=80");

            Product p4 = new Product();
            p4.setName("Ergonomic Mechanical Keyboard");
            p4.setDescription("Mechanical gaming keyboard with tactile brown switches, customizable RGB backlighting, and solid aluminum frame.");
            p4.setPrice(5999.0);
            p4.setImageUrl("https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500&q=80");

            Product p5 = new Product();
            p5.setName("Smart Fitness Band");
            p5.setDescription("Track your steps, heart rate, sleep quality, and workouts. Features a vibrant AMOLED display and 14-day battery.");
            p5.setPrice(2499.0);
            p5.setImageUrl("https://images.unsplash.com/photo-1575311373937-040b8e1fd5b6?w=500&q=80");

            Product p6 = new Product();
            p6.setName("Organic Arabica Coffee Beans");
            p6.setDescription("Medium roast, single-origin whole bean coffee. Rich flavor notes of chocolate and dark berries. 500g pack.");
            p6.setPrice(899.0);
            p6.setImageUrl("https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=500&q=80");

            productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
            System.out.println("✅ Database seeded with default products!");
        }
    }
}

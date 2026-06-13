package com.nitin.ecommerce.service;

import com.nitin.ecommerce.model.User;
import com.nitin.ecommerce.repository.UserRepository;
import com.nitin.ecommerce.security.JwtUtil;  // ← Yeh add karo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // ── DEPENDENCIES ──────────────────────
    
    // Database se baat karne ke liye
    @Autowired
    private UserRepository userRepository;

    // Password encrypt karne ke liye
    @Autowired
    private PasswordEncoder passwordEncoder;

    // JWT Token banane ke liye
    @Autowired
    private JwtUtil jwtUtil;

    // ── REGISTER ──────────────────────────
    // Naya user banao
    public User registerUser(User user) {
        // Password encrypt karo
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Role set karo
        user.setRole("USER");
        // Database mein save karo
        return userRepository.save(user);
    }

    // ── LOGIN ─────────────────────────────
    // User login karo — Token do
    public String loginUser(String email, String password) {
        // Step 1 — Email se user dhundho
        User user = userRepository.findByEmail(email);

        // Step 2 — User mila ya nahi
        if (user == null) {
            return "Invalid Email";
        }

        // Step 3 — Password match karo
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid Password";
        }

        // Step 4 — Sab sahi — Token banao aur do
        return jwtUtil.generateToken(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
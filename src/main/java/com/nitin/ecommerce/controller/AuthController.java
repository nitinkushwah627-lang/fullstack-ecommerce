package com.nitin.ecommerce.controller;

import com.nitin.ecommerce.model.User;
import com.nitin.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Service required for operations
    @Autowired
    private UserService userService;

    // ── REGISTER API ──────────────────────
    // URL: POST /api/auth/register
    // Task: Create new user
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // ── LOGIN API ─────────────────────────
    // URL: POST /api/auth/login
    // Task: Login and get JWT Token
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        // Get email and password
        String email = request.get("email");
        String password = request.get("password");
        // Pass to service to get token back
        return userService.loginUser(email, password);
    }

    // ── ME API ────────────────────────────
    // URL: GET /api/auth/me
    // Task: Get your details
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        User user = userService.getUserByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        user.setPassword(null); // Security first
        return ResponseEntity.ok(user);
    }
}
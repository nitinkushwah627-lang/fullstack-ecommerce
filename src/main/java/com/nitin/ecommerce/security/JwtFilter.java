package com.nitin.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Header se token lo
        String authHeader = request.getHeader("Authorization");

        // 2. Token hai ya nahi check karo
        if (authHeader != null &&
            authHeader.startsWith("Bearer ")) {

            // 3. Token nikalo
            String token = authHeader.substring(7);

            // 4. Token sahi hai?
            if (jwtUtil.isValid(token)) {

                // 5. Email nikalo
                String email = jwtUtil.getEmail(token);

                // 6. User login mark karo
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                        email, null, new ArrayList<>()
                    );
                SecurityContextHolder.getContext()
                    .setAuthentication(auth);
            }
        }

        // 7. Aage bhejo request
        filterChain.doFilter(request, response);
    }
}
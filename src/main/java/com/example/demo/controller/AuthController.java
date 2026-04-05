package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomUserDetailsService userDetailsService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        
        if (!passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        final String jwt = jwtUtils.generateToken(userDetails);
        String role = userDetails.getAuthorities().stream().findFirst().map(a -> a.getAuthority().replace("ROLE_", "")).orElse("VIEWER");

        return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getUsername(), role));
    }
}

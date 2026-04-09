package com.klef.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.klef.sdp.dto.AuthRequestDTO;
import com.klef.sdp.security.JwtUtil;
import com.klef.sdp.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController 
{
    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) 
    {
        try 
        {
            UserDetails userDetails = service.loadUserByUsername(request.getLogin());

            String role = userDetails.getAuthorities()
                    .iterator().next().getAuthority(); // ROLE_ADMIN

            // Normalize role (remove ROLE_)
            String cleanRole = role.replace("ROLE_", "");

            // Normalize request role too
            String requestRole = request.getRole().replace("ROLE_", "");

            // ✅ FIXED ROLE CHECK
            if (!cleanRole.equalsIgnoreCase(requestRole))
            {
                return ResponseEntity.status(403).body("Invalid Role");
            }

            boolean isValid = false;

            // ✅ ADMIN password check (plain or encoded depending on your DB)
            if (cleanRole.equalsIgnoreCase("ADMIN"))
            {
                isValid = request.getPassword().equals(userDetails.getPassword());
            }
            // ✅ ARTIST / VISITOR BCrypt check
            else if (cleanRole.equalsIgnoreCase("ARTIST") || cleanRole.equalsIgnoreCase("VISITOR"))
            {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                isValid = encoder.matches(request.getPassword(), userDetails.getPassword());
            }
            else
            {
                return ResponseEntity.status(403).body("Invalid Role Type");
            }

            if (!isValid)
            {
                return ResponseEntity.status(401).body("Login Invalid");
            }

            // Generate JWT
            String token = jwtUtil.generateToken(userDetails);

            Object userObj = service.getUserByLogin(request.getLogin());

            return ResponseEntity.ok(
                Map.of(
                    "token", token,
                    "role", role, // still send ROLE_ADMIN
                    "user", userObj
                )
            );
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
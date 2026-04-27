package com.klef.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.klef.sdp.dto.AuthRequestDTO;
import com.klef.sdp.dto.EmailDTO;
import com.klef.sdp.security.JwtUtil;
import com.klef.sdp.service.EmailService;
import com.klef.sdp.service.OtpService;
import com.klef.sdp.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Adjust based on your frontend URL, e.g., "http://localhost:5173"
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody EmailDTO request) {
        try {
            String email = request.getReceiveremail();
            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }

            String otp = otpService.generateOtp(email);
            emailService.sendOtpEmail(email, otp);
            
            return ResponseEntity.ok(Map.of("message", "OTP sent successfully to " + email));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending OTP: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody EmailDTO request) {
        String email = request.getReceiveremail();
        String otp = request.getMessage(); // Assuming 'message' holds the OTP from the client

        if (email == null || otp == null) {
            return ResponseEntity.badRequest().body("Email and OTP are required");
        }

        boolean isValid = otpService.verifyOtp(email, otp);

        if (isValid) {
            return ResponseEntity.ok(Map.of("message", "OTP Verified Successfully"));
        } else {
            return ResponseEntity.status(400).body("Invalid OTP or OTP expired");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        try {
            String loginIdentifier = request.getLogin(); // e.g., email or username
            
            String requestRole = request.getRole() != null ? request.getRole().replace("ROLE_", "") : "";

            // 1. Load UserDetails and authenticate explicitly for the requested role
            UserDetails userDetails = userService.loadUserByUsernameAndRole(loginIdentifier, requestRole);

            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String cleanRole = role.replace("ROLE_", "");

            if (!cleanRole.equalsIgnoreCase(requestRole)) {
                return ResponseEntity.status(403).body("Invalid Role for this user");
            }

            boolean isPasswordValid = false;
            
            if (cleanRole.equalsIgnoreCase("ADMIN")) {
                isPasswordValid = request.getPassword().equals(userDetails.getPassword());
            } else if (cleanRole.equalsIgnoreCase("ARTIST") || cleanRole.equalsIgnoreCase("VISITOR")) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                isPasswordValid = encoder.matches(request.getPassword(), userDetails.getPassword());
            } else {
                return ResponseEntity.status(403).body("Invalid Role Type");
            }

            if (!isPasswordValid) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
            
            // 2. Check if user is OTP-verified (ONLY FOR VISITOR)
            if (cleanRole.equalsIgnoreCase("VISITOR")) {
                if (!otpService.isVerified(loginIdentifier)) {
                    return ResponseEntity.ok(Map.of("requiresOtp", true, "message", "OTP_REQUIRED"));
                }
            }

            // 3. Generate JWT Token
            String token = jwtUtil.generateToken(userDetails);
            Object userObj = userService.getUserByLoginAndRole(loginIdentifier, cleanRole);

            // 4. Clear OTP verification status after successful login for security
            otpService.clearVerification(loginIdentifier);

            return ResponseEntity.ok(Map.of(
                "token", token,
                "role", role,
                "user", userObj
            ));

        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Login Error: " + e.getMessage());
        }
    }
}
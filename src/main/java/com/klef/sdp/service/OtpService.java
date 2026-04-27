package com.klef.sdp.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    // Using ConcurrentHashMap for thread safety in multi-threaded environments
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, Boolean> verifiedUsers = new ConcurrentHashMap<>();

    public String generateOtp(String email) {
        // Generate a 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);
        verifiedUsers.put(email, false); // Reset verification status upon new OTP
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email); // Expire/clear OTP immediately after successful verification
            verifiedUsers.put(email, true); // Mark user as verified
            return true;
        }
        return false;
    }

    public boolean isVerified(String email) {
        return verifiedUsers.getOrDefault(email, false);
    }

    public void clearVerification(String email) {
        verifiedUsers.remove(email);
    }
}
package com.klef.sdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("OTP for Login Verification");
        message.setText("Your One-Time Password (OTP) for login is: " + otp + 
                        ".\n\nPlease do not share this OTP with anyone.");
        
        mailSender.send(message);
    }
}
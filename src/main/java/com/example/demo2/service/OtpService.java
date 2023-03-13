package com.example.demo2.service;

import com.example.demo2.model.EmailDetails;
import com.example.demo2.model.Otp;
import com.example.demo2.repository.OtpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class OtpService {

    private static final String OTP_EMAIL_SUBJECT = "OTP Verification Code";
    private static final int OTP_EXPIRY_TIME = 5; // in minutes
    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpRepository otpRepository;

    public ResponseEntity<?> sendOtp(String email) {
        // Generate random OTP
        int otp = new Random().nextInt(999999);

        // Save OTP with expiry time to database
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_EXPIRY_TIME);
        Otp otpEntity = new Otp(email, otp, expiryTime);
        otpRepository.save(otpEntity);

        // Send OTP to user via email
        String eRes = emailService.sendSimpleMail(
                new EmailDetails(email, "Your OTP Verification Code is: " + otp, OTP_EMAIL_SUBJECT));
        if (eRes.equals("Mail Sent Successfully")) {
            return ResponseEntity.ok(Map.of("message", "Otp sent"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Error while Sending Mail"));
        }
    }

    @Transactional
    public ResponseEntity<?> verifyOtp(String email, int otp) {
        // Retrieve OTP from database
        Otp otpEntity = otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);
        if (otpEntity == null || otpEntity.getOtp() != otp) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Invalid OTP",
                    "status", false
            ));
        }
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "OTP has been Expired",
                    "status", false
            ));
        }
        log.info(String.valueOf(otpEntity.getId()));
        // Delete OTP from database
        otpRepository.deleteAllByEmail(email);
        return ResponseEntity.ok(Map.of(
                "message", "OTP verified successfully",
                "status", true
        ));
    }
}

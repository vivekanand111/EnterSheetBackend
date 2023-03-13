package com.example.demo2.controller;

import com.example.demo2.request.OtpRequest;
import com.example.demo2.service.OtpService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/otp")
@Log4j2
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class OtpController {
    private final OtpService otpService;

    @PostMapping(value = "/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest otpRequest) {
        return otpService.sendOtp(otpRequest.getEmail());
    }

    @PostMapping(value = "/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
        try {
            return otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Failed to verify OTP",
                    "status", false
            ));
        }
    }
}

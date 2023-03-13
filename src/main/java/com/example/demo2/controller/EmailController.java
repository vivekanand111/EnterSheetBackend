package com.example.demo2.controller;

import com.example.demo2.model.EmailDetails;
import com.example.demo2.service.EmailService;
import com.example.demo2.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/mail")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    // Sending email with attachment
    @PostMapping(value = "/sendMailWithAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendMailWithAttachment(
            @RequestParam("file") MultipartFile file,
            @RequestParam("recipient") String recipient,
            @RequestParam("msgBody") String msgBody,
            @RequestParam("subject") String subject
    ) {
        EmailDetails details = new EmailDetails(recipient, msgBody, subject);
        String status = emailService.sendMailWithAttachment(details, file);
        return status;
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, Object> request) {
        String email = (String) request.get("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        return emailService.forgotPassword(email);
    }
    @GetMapping("/verifyPasswordToken/{token}")
    public ResponseEntity<String> verifyPasswordToken(@PathVariable String token) {
        //System.out.println(token);
        return userService.verifyPasswordToken(token);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, Object> request) {
        String token = (String) request.get("token");
        String newPassword = (String) request.get("password");
        return userService.resetPassword(token,newPassword);
    }
}

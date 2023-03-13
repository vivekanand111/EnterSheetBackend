package com.example.demo2.service;

import com.example.demo2.model.EmailDetails;
import com.example.demo2.model.User;
import com.example.demo2.repository.UserRepository;
import com.example.demo2.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenManager tokenManager;
    @Value("${spring.mail.username}")
    private String sender;

    // Method 1: To send a simple email
    public String sendSimpleMail(EmailDetails details) {
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setSubject(details.getSubject());
            mailMessage.setText(details.getMsgBody());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully";
        } catch (Exception e) {
            System.out.println(e);
            return "Error while Sending Mail";
        }
    }

    // Method 2 send an email with attachment
    public String sendMailWithAttachment(EmailDetails details, MultipartFile file) {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setSubject(details.getSubject());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.addAttachment(file.getOriginalFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }

    public ResponseEntity<?> forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }
        // Generate a new password reset token
        String token = tokenManager.generatePasswordResetToken(String.valueOf(user.getId()));
        // Send a password reset email to the user
        String subject = "Password Reset";
        String message = "Please click on the following link to reset your password:\n\n"
                + "http://localhost:5173/resetpassword?token=" + token;
        String eRes = sendSimpleMail(new EmailDetails(email, message, subject));
        if (eRes.equals("Mail Sent Successfully")) {
            return ResponseEntity.ok(Map.of("message", "Reset Password Sent to Mail Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Error while Sending Mail"));
        }
    }
}

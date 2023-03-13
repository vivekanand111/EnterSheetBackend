package com.example.demo2.service;

import com.example.demo2.exception.NoRecordExistsException;
import com.example.demo2.exception.RecordAlreadyExistsException;
import com.example.demo2.model.User;
import com.example.demo2.repository.UserRepository;
import com.example.demo2.request.LoginRequest;
import com.example.demo2.security.TokenManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getById(int id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoRecordExistsException("User Not Found With id: " + id));
        return user;
    }

    public User addUser(User newUser) {
        List<User> userList = userRepository.findAllByEmail(newUser.getEmail());
        if (userList.size() > 0) {
            throw new RecordAlreadyExistsException("User Already Registered with email " + newUser.getEmail());
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User updateUser(User u) {
        User user = userRepository.findById(u.getId()).orElseThrow(() ->
                new NoRecordExistsException("User Not Found With id: " + u.getId()));
        user.setEmail(u.getEmail());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        user.setUsername(u.getUsername());
        user.setMobileNumber(u.getMobileNumber());
        user.setRole(u.getRole());
        return userRepository.save(user);
    }

    public User login(LoginRequest l) {
        List<User> userList = userRepository.findAllByEmail(l.getEmail());
        if (userList.size() > 1) {
            throw new RecordAlreadyExistsException("Multiple Users Already there with email " + l.getEmail());
        } else if (userList.size() == 0) {
            throw new NoRecordExistsException("User not registered with email " + l.getEmail());
        }
        User u = userList.get(0);
        if (passwordEncoder.matches(l.getPassword(), u.getPassword())) {
            return u;
        } else {
            throw new NoRecordExistsException("Incorrect password for email " + l.getEmail());
        }
    }

    public String deleteUser(Integer uid) {
        userRepository.findById(uid).orElseThrow(() ->
                new NoRecordExistsException("User Not Found With id: " + uid));
        userRepository.deleteById(uid);
        return "Success";
    }

    public Object authenticate(String email, String password) {
        List<User> userList = userRepository.findAllByEmail(email);
        if (userList.size() > 1) {
            throw new RecordAlreadyExistsException("Multiple Users Already there with email " + email);
        } else if (userList.size() == 0) {
            throw new NoRecordExistsException("User not registered with email " + email);
        }
        User u = userList.get(0);
        if (password.equals(u.getPassword())) {
            return u;
        } else {
            throw new NoRecordExistsException("Incorrect password for email " + email);
        }
    }

    //password Reset
    public ResponseEntity<String> verifyPasswordToken(String token) {
        Integer userId=tokenManager.getUserIdFromPasswordResetToken(token);
        if (userId == null) {
            return ResponseEntity.badRequest().body("The Reset Link has been Expired or Invalid");
        }
        // Token is valid, return a success response
        return ResponseEntity.ok().body("Token verified");
    }

    public ResponseEntity<String> resetPassword(String token, String newPassword) {
        Integer userId=tokenManager.getUserIdFromPasswordResetToken(token);
        if (userId == null) {
            return ResponseEntity.badRequest().body("The Reset Link has been Expired or Invalid");
        }
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.badRequest().body("Their is No User to Access this Reset Link");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok().body("Password Reset Successful");
    }
}

package com.example.demo2.controller;

import com.example.demo2.model.Report;
import com.example.demo2.model.User;
import com.example.demo2.request.AdminRequest;
import com.example.demo2.request.LoginRequest;
import com.example.demo2.response.UserTasksResponse;
import com.example.demo2.service.ReportService;
import com.example.demo2.service.TaskService;
import com.example.demo2.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
@Log4j2
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class UserController {
    private final UserService userService;
    private final ReportService reportService;
    private final TaskService taskService;

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable final int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<User> addUser(@Valid @RequestBody User userRequest) {
        return ResponseEntity.ok(userService.addUser(userRequest));
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User u) {
        return ResponseEntity.ok(userService.updateUser(u));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest l) {
        return ResponseEntity.ok(userService.login(l));
    }

    @GetMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(userService.authenticate(userDetails.getUsername(),userDetails.getPassword()));
    }

    @GetMapping(value = "/{uid}/reports")
    public ResponseEntity<List<Report>> getUserReports(@PathVariable Integer uid) {
        return ResponseEntity.ok(reportService.getUserReports(uid));
    }

    @PostMapping(value = "/getTasksBetweenDates")
    public ResponseEntity<List<UserTasksResponse>> getTasksBetweenDates(@Valid @RequestBody AdminRequest reqBody) {
        return ResponseEntity.ok(taskService.getTasksBetweenDates2(reqBody));
    }

    @DeleteMapping(value = "{uid}/deleteUser")
    public ResponseEntity<String> deleteReport(@PathVariable Integer uid) {
        return ResponseEntity.ok(userService.deleteUser(uid));
    }

    /*@ExceptionHandler(value = NoRecordExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNoRecordExistsException(NoRecordExistsException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(),ex.getMessage());
    }*/
}
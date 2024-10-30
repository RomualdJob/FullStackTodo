package com.way2p.todo.controllers;

import com.way2p.todo.dto.SignupRequest;
import com.way2p.todo.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/signup")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SignupController {

    private final AuthService authService;

    public SignupController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> signupCustomer(@RequestBody SignupRequest signupRequest) {
        boolean isUserCreated = authService.createCustomer(signupRequest);

        Map<String, String> response = new HashMap<>();
        if (isUserCreated) {
            response.put("message", "Customer created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Failed to create customer");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

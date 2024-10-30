package com.way2p.todo.controllers;

import com.way2p.todo.dto.LoginRequest;
import com.way2p.todo.dto.LoginResponse;
import com.way2p.todo.services.jwt.CustomerServiceImpl;
import com.way2p.todo.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final CustomerServiceImpl customerService;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, CustomerServiceImpl customerService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping // plus de "/login" ici
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authentification
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
            );

            // Chargement des détails de l'utilisateur
            UserDetails userDetails = customerService.loadUserByUsername(loginRequest.email);

            // Génération du token JWT
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            System.out.println(ResponseEntity.ok(new LoginResponse(jwt)));
            // Retourne la réponse avec le token
            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (AuthenticationException e) {
            logger.error("Login failed for user: " + loginRequest.email, e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}

package com.project.ecom.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecom.dto.LoginRequest;
import com.project.ecom.dto.RegisterRequest;
import com.project.ecom.kafka.UserLoggedInEvent;
import com.project.ecom.kafka.UserRegisteredEvent;
import com.project.ecom.model.Role;
import com.project.ecom.model.User;
import com.project.ecom.repository.UserRepository;
import com.project.ecom.service.ExtractJtiFromJwtToken;
import com.project.ecom.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private  AuthenticationManager authManager;
    private  JwtService jwtService;
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    
    @Autowired
    ExtractJtiFromJwtToken jti;
    
    private static final String TOPIC = "user-events"; 
    
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    public AuthController(
        AuthenticationManager authManager,
        JwtService jwtService,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    	
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getUsername())
                .orElseThrow();
        String token = jwtService.generateToken(user.getUsername());
        UserLoggedInEvent event = new UserLoggedInEvent(
                user.getId(),
                token,  // Or extract JTI if using opaque tokens
                Instant.now()
            );
            
            kafkaTemplate.send(TOPIC, "user.logged_in", event);
        return ResponseEntity.ok(Map.of("token", token));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    	if (userRepository.findByEmail(request.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("User with this email already exists");
        }
        User user = new User();
        user.setEmail(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role requestedRole = request.getRole();
        if (requestedRole != Role.USER && requestedRole != Role.SELLER) {
            return ResponseEntity.badRequest().body("Invalid role selection");
        }
        user.setRole(requestedRole);
        
        UserRegisteredEvent event = new UserRegisteredEvent(
        		user.getId(),
        		user.getEmail(),
        		user.getRole(),
                Instant.now()
            );

            kafkaTemplate.send(TOPIC, "user.registered", event);
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }
}

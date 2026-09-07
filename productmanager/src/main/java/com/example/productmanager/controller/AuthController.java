package com.example.productmanager.controller;

import com.example.productmanager.dto.LoginRequest;
import com.example.productmanager.dto.LoginResponse;
import com.example.productmanager.dto.SignUpRequest;
import com.example.productmanager.dto.SignUpResponse;
import com.example.productmanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService){
            this.authService=authService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> Signup(@Valid @RequestBody SignUpRequest signUpRequest){
        SignUpResponse signUpResponse =authService.signup(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpResponse);
    }
}

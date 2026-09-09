package com.example.productmanager.service;

import com.example.productmanager.dto.LoginRequest;
import com.example.productmanager.dto.LoginResponse;
import com.example.productmanager.dto.SignUpRequest;
import com.example.productmanager.dto.SignUpResponse;
import com.example.productmanager.entity.User;
import com.example.productmanager.exception.InvalidCredentialsException;
import com.example.productmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public SignUpResponse signup(SignUpRequest signUpRequest){
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setId(savedUser.getId());
        signUpResponse.setUsername(savedUser.getUsername());
        signUpResponse.setEmail(savedUser.getEmail());
        signUpResponse.setRole(savedUser.getRole());
        return signUpResponse;
    }
    public LoginResponse login(LoginRequest loginRequest){
        User user = userRepository.findByUsername(
                loginRequest.getUsername()
        ).orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
        if(!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        )){
            throw new InvalidCredentialsException("Invalid username or password");
        }
        LoginResponse loginResponse = new LoginResponse();
        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        loginResponse.setToken(token);

        return loginResponse;
    }
}

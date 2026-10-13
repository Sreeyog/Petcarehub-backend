package com.petcarehub.controller;

import com.petcarehub.dto.AuthRequest;
import com.petcarehub.dto.AuthResponse;
import com.petcarehub.dto.RegisterRequest;
import com.petcarehub.dto.UserDto;
import com.petcarehub.entity.User;
import com.petcarehub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        User user = authService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(new UserDto(user));
    }
}

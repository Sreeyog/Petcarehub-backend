package com.petcarehub.service;

import com.petcarehub.dto.AuthRequest;
import com.petcarehub.dto.AuthResponse;
import com.petcarehub.dto.RegisterRequest;
import com.petcarehub.dto.UserDto;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;
import com.petcarehub.repository.UserRepository;
import com.petcarehub.security.JwtService;
import com.petcarehub.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered: " + request.getEmail());
        }

        Role role = request.getRole() != null ? request.getRole() : Role.ROLE_PET_OWNER;

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                request.getPhone(),
                role,
                request.getAddress(),
                request.getClinicOrBusinessName(),
                request.getSpecialization()
        );

        User saved = userRepository.save(user);
        UserPrincipal principal = new UserPrincipal(saved);
        String token = jwtService.generateToken(principal, saved.getId(), saved.getRole().name());

        return new AuthResponse(token, new UserDto(saved));
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        UserPrincipal principal = new UserPrincipal(user);
        String token = jwtService.generateToken(principal, user.getId(), user.getRole().name());

        return new AuthResponse(token, new UserDto(user));
    }

    public User getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("No authenticated user found in security context");
        }
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + email));
    }
}

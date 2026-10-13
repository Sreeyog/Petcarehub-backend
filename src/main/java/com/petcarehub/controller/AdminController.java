package com.petcarehub.controller;

import com.petcarehub.dto.UserDto;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;
import com.petcarehub.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(
                userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList())
        );
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<UserDto> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        String roleStr = payload.get("role");
        if (roleStr != null) {
            user.setRole(Role.valueOf(roleStr));
            userRepository.save(user);
        }

        return ResponseEntity.ok(new UserDto(user));
    }

    @PatchMapping("/users/{id}/toggle-status")
    public ResponseEntity<UserDto> toggleUserStatus(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        user.setEnabled(!user.isEnabled());
        userRepository.save(user);

        return ResponseEntity.ok(new UserDto(user));
    }
}

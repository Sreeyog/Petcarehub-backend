package com.petcarehub.controller;

import com.petcarehub.dto.MedicalRecordRequest;
import com.petcarehub.entity.MedicalRecord;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;
import com.petcarehub.service.AuthService;
import com.petcarehub.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final AuthService authService;

    public MedicalRecordController(MedicalRecordService medicalRecordService, AuthService authService) {
        this.medicalRecordService = medicalRecordService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> createRecord(@Valid @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.ok(medicalRecordService.createRecord(request));
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllRecords() {
        User currentUser = authService.getCurrentAuthenticatedUser();
        if (currentUser.getRole() == Role.ROLE_VETERINARIAN) {
            return ResponseEntity.ok(medicalRecordService.getRecordsByCurrentVet());
        }
        return ResponseEntity.ok(medicalRecordService.getAllRecords());
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MedicalRecord>> getRecordsByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(medicalRecordService.getRecordsByPet(petId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getRecordById(id));
    }
}

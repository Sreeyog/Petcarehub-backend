package com.petcarehub.controller;

import com.petcarehub.dto.VaccinationRequest;
import com.petcarehub.entity.VaccinationRecord;
import com.petcarehub.service.VaccinationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    private final VaccinationService vaccinationService;

    public VaccinationController(VaccinationService vaccinationService) {
        this.vaccinationService = vaccinationService;
    }

    @PostMapping
    public ResponseEntity<VaccinationRecord> createVaccination(@Valid @RequestBody VaccinationRequest request) {
        return ResponseEntity.ok(vaccinationService.createVaccination(request));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<VaccinationRecord>> getVaccinationsByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(vaccinationService.getVaccinationsByPet(petId));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<VaccinationRecord>> getUpcomingVaccinations() {
        return ResponseEntity.ok(vaccinationService.getUpcomingOrOverdueVaccinations());
    }
}

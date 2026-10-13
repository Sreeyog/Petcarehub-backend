package com.petcarehub.controller;

import com.petcarehub.dto.AppointmentRequest;
import com.petcarehub.entity.Appointment;
import com.petcarehub.entity.AppointmentStatus;
import com.petcarehub.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() {
        return ResponseEntity.ok(appointmentService.getAppointmentsForCurrentUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPet(petId));
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload
    ) {
        String statusStr = payload.get("status");
        if (statusStr == null) {
            throw new IllegalArgumentException("Status is required");
        }
        AppointmentStatus status = AppointmentStatus.valueOf(statusStr.toUpperCase());
        String providerNotes = payload.get("providerNotes");

        return ResponseEntity.ok(appointmentService.updateStatus(id, status, providerNotes));
    }
}

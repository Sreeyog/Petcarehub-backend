package com.petcarehub.controller;

import com.petcarehub.dto.DashboardStatsDto;
import com.petcarehub.dto.ServiceRequest;
import com.petcarehub.dto.UserDto;
import com.petcarehub.entity.ServiceCategory;
import com.petcarehub.entity.ServiceEntity;
import com.petcarehub.service.CareService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ServiceController {

    private final CareService careService;

    public ServiceController(CareService careService) {
        this.careService = careService;
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(careService.getAllActiveServices());
    }

    @GetMapping("/services/category/{category}")
    public ResponseEntity<List<ServiceEntity>> getServicesByCategory(@PathVariable ServiceCategory category) {
        return ResponseEntity.ok(careService.getServicesByCategory(category));
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(careService.getServiceById(id));
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceEntity> createService(@Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(careService.createService(request));
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable Long id, @Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(careService.updateService(id, request));
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        careService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/providers")
    public ResponseEntity<List<UserDto>> getCareProviders() {
        return ResponseEntity.ok(careService.getAllCareProviders());
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        return ResponseEntity.ok(careService.getDashboardStats());
    }
}

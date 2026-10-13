package com.petcarehub.controller;

import com.petcarehub.dto.PetRequest;
import com.petcarehub.entity.Pet;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;
import com.petcarehub.service.AuthService;
import com.petcarehub.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;
    private final AuthService authService;

    public PetController(PetService petService, AuthService authService) {
        this.petService = petService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getPets() {
        User currentUser = authService.getCurrentAuthenticatedUser();
        if (currentUser.getRole() == Role.ROLE_PET_OWNER) {
            return ResponseEntity.ok(petService.getMyPets());
        }
        // Vets, providers, and admins can view all pets
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Pet>> getMyPets() {
        return ResponseEntity.ok(petService.getMyPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@Valid @RequestBody PetRequest request) {
        return ResponseEntity.ok(petService.createPet(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @Valid @RequestBody PetRequest request) {
        return ResponseEntity.ok(petService.updatePet(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}

package com.petcarehub.service;

import com.petcarehub.dto.PetRequest;
import com.petcarehub.entity.Pet;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;
import com.petcarehub.repository.PetRepository;
import com.petcarehub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public PetService(PetRepository petRepository, UserRepository userRepository, AuthService authService) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Transactional
    public Pet createPet(PetRequest request) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        User owner;

        // If admin or vet specifies an ownerId, use that; otherwise current authenticated user is owner
        if ((currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_VETERINARIAN) && request.getOwnerId() != null) {
            owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new IllegalArgumentException("Specified owner not found with id: " + request.getOwnerId()));
        } else {
            owner = currentUser;
        }

        Pet pet = new Pet(
                request.getName(),
                request.getSpecies(),
                request.getBreed(),
                request.getDateOfBirth(),
                request.getGender(),
                request.getWeightKg(),
                request.getColor(),
                request.getMicrochipNumber(),
                request.getAllergies(),
                request.getSpecialNotes(),
                request.getPhotoUrl(),
                owner
        );

        return petRepository.save(pet);
    }

    @Transactional
    public Pet updatePet(Long id, PetRequest request) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + id));

        // Security check: only pet owner, vet, or admin can update
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_VETERINARIAN && !pet.getOwner().getId().equals(currentUser.getId())) {
            throw new SecurityException("Unauthorized to update pet details for pet id: " + id);
        }

        pet.setName(request.getName());
        pet.setSpecies(request.getSpecies());
        pet.setBreed(request.getBreed());
        pet.setDateOfBirth(request.getDateOfBirth());
        pet.setGender(request.getGender());
        pet.setWeightKg(request.getWeightKg());
        pet.setColor(request.getColor());
        pet.setMicrochipNumber(request.getMicrochipNumber());
        pet.setAllergies(request.getAllergies());
        pet.setSpecialNotes(request.getSpecialNotes());
        if (request.getPhotoUrl() != null && !request.getPhotoUrl().isBlank()) {
            pet.setPhotoUrl(request.getPhotoUrl());
        }

        return petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + id));
    }

    public List<Pet> getMyPets() {
        User currentUser = authService.getCurrentAuthenticatedUser();
        return petRepository.findByOwner(currentUser);
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Transactional
    public void deletePet(Long id) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + id));

        if (currentUser.getRole() != Role.ROLE_ADMIN && !pet.getOwner().getId().equals(currentUser.getId())) {
            throw new SecurityException("Unauthorized to delete pet id: " + id);
        }

        petRepository.delete(pet);
    }
}

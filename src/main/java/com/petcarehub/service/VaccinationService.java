package com.petcarehub.service;

import com.petcarehub.dto.VaccinationRequest;
import com.petcarehub.entity.Pet;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;
import com.petcarehub.entity.VaccinationRecord;
import com.petcarehub.repository.PetRepository;
import com.petcarehub.repository.VaccinationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccinationService {

    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final PetRepository petRepository;
    private final AuthService authService;

    public VaccinationService(VaccinationRecordRepository vaccinationRecordRepository, PetRepository petRepository, AuthService authService) {
        this.vaccinationRecordRepository = vaccinationRecordRepository;
        this.petRepository = petRepository;
        this.authService = authService;
    }

    @Transactional
    public VaccinationRecord createVaccination(VaccinationRequest request) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + request.getPetId()));

        // Vet, Admin, or the pet's owner can record a vaccination
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_VETERINARIAN && !pet.getOwner().getId().equals(currentUser.getId())) {
            throw new SecurityException("Unauthorized to record vaccination for this pet");
        }

        VaccinationRecord record = new VaccinationRecord(
                pet,
                request.getVaccineName(),
                request.getAdministeredDate(),
                request.getNextDueDate(),
                request.getBatchNumber(),
                currentUser,
                request.getClinicNotes()
        );

        return vaccinationRecordRepository.save(record);
    }

    public List<VaccinationRecord> getVaccinationsByPet(Long petId) {
        return vaccinationRecordRepository.findByPetIdOrderByAdministeredDateDesc(petId);
    }

    public List<VaccinationRecord> getUpcomingOrOverdueVaccinations() {
        return vaccinationRecordRepository.findByNextDueDateBefore(LocalDate.now().plusDays(30));
    }
}

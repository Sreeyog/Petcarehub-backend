package com.petcarehub.service;

import com.petcarehub.dto.MedicalRecordRequest;
import com.petcarehub.entity.MedicalRecord;
import com.petcarehub.entity.Pet;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;
import com.petcarehub.repository.MedicalRecordRepository;
import com.petcarehub.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PetRepository petRepository;
    private final AuthService authService;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PetRepository petRepository, AuthService authService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.petRepository = petRepository;
        this.authService = authService;
    }

    @Transactional
    public MedicalRecord createRecord(MedicalRecordRequest request) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        if (currentUser.getRole() != Role.ROLE_VETERINARIAN && currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new SecurityException("Only licensed veterinarians or administrators can create medical diagnostic records");
        }

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + request.getPetId()));

        MedicalRecord record = new MedicalRecord(
                pet,
                currentUser,
                request.getRecordDate(),
                request.getDiagnosis(),
                request.getSymptoms(),
                request.getTreatmentPlan(),
                request.getPrescribedMedications(),
                request.getVitalSigns(),
                request.getNextFollowUpDate(),
                request.getLabResultsSummary()
        );

        return medicalRecordRepository.save(record);
    }

    public List<MedicalRecord> getRecordsByPet(Long petId) {
        return medicalRecordRepository.findByPetIdOrderByRecordDateDesc(petId);
    }

    public List<MedicalRecord> getRecordsByCurrentVet() {
        User currentUser = authService.getCurrentAuthenticatedUser();
        return medicalRecordRepository.findByVeterinarianIdOrderByRecordDateDesc(currentUser.getId());
    }

    public List<MedicalRecord> getAllRecords() {
        return medicalRecordRepository.findAllByOrderByRecordDateDesc();
    }

    public MedicalRecord getRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medical record not found with id: " + id));
    }
}

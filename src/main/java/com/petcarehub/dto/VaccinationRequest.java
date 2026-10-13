package com.petcarehub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class VaccinationRequest {

    @NotNull(message = "Pet ID is required")
    private Long petId;

    @NotBlank(message = "Vaccine name is required")
    private String vaccineName;

    private LocalDate administeredDate = LocalDate.now();
    private LocalDate nextDueDate;
    private String batchNumber;
    private String clinicNotes;

    public VaccinationRequest() {
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public LocalDate getAdministeredDate() {
        return administeredDate != null ? administeredDate : LocalDate.now();
    }

    public void setAdministeredDate(LocalDate administeredDate) {
        this.administeredDate = administeredDate;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getClinicNotes() {
        return clinicNotes;
    }

    public void setClinicNotes(String clinicNotes) {
        this.clinicNotes = clinicNotes;
    }
}

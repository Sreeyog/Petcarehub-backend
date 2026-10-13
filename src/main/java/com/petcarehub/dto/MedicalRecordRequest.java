package com.petcarehub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MedicalRecordRequest {

    @NotNull(message = "Pet ID is required")
    private Long petId;

    private LocalDate recordDate = LocalDate.now();

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    private String symptoms;
    private String treatmentPlan;
    private String prescribedMedications;
    private String vitalSigns;
    private LocalDate nextFollowUpDate;
    private String labResultsSummary;

    public MedicalRecordRequest() {
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public LocalDate getRecordDate() {
        return recordDate != null ? recordDate : LocalDate.now();
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    public String getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(String vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    public LocalDate getNextFollowUpDate() {
        return nextFollowUpDate;
    }

    public void setNextFollowUpDate(LocalDate nextFollowUpDate) {
        this.nextFollowUpDate = nextFollowUpDate;
    }

    public String getLabResultsSummary() {
        return labResultsSummary;
    }

    public void setLabResultsSummary(String labResultsSummary) {
        this.labResultsSummary = labResultsSummary;
    }
}

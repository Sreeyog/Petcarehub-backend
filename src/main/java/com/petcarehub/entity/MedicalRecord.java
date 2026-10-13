package com.petcarehub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vet_id", nullable = false)
    @JsonIgnoreProperties({"password", "createdAt"})
    private User veterinarian;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false, length = 200)
    private String diagnosis;

    @Column(length = 1000)
    private String symptoms;

    @Column(length = 2000)
    private String treatmentPlan;

    @Column(length = 1000)
    private String prescribedMedications;

    @Column(length = 250)
    private String vitalSigns;

    private LocalDate nextFollowUpDate;

    @Column(length = 1000)
    private String labResultsSummary;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public MedicalRecord() {
    }

    public MedicalRecord(Pet pet, User veterinarian, LocalDate recordDate, String diagnosis, String symptoms, String treatmentPlan, String prescribedMedications, String vitalSigns, LocalDate nextFollowUpDate, String labResultsSummary) {
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.recordDate = recordDate;
        this.diagnosis = diagnosis;
        this.symptoms = symptoms;
        this.treatmentPlan = treatmentPlan;
        this.prescribedMedications = prescribedMedications;
        this.vitalSigns = vitalSigns;
        this.nextFollowUpDate = nextFollowUpDate;
        this.labResultsSummary = labResultsSummary;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(User veterinarian) {
        this.veterinarian = veterinarian;
    }

    public LocalDate getRecordDate() {
        return recordDate;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

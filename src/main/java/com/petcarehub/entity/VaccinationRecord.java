package com.petcarehub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vaccination_records")
public class VaccinationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(nullable = false, length = 120)
    private String vaccineName;

    @Column(nullable = false)
    private LocalDate administeredDate;

    private LocalDate nextDueDate;

    @Column(length = 60)
    private String batchNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "administered_by_id")
    @JsonIgnoreProperties({"password", "createdAt"})
    private User administeredBy;

    @Column(length = 500)
    private String clinicNotes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public VaccinationRecord() {
    }

    public VaccinationRecord(Pet pet, String vaccineName, LocalDate administeredDate, LocalDate nextDueDate, String batchNumber, User administeredBy, String clinicNotes) {
        this.pet = pet;
        this.vaccineName = vaccineName;
        this.administeredDate = administeredDate;
        this.nextDueDate = nextDueDate;
        this.batchNumber = batchNumber;
        this.administeredBy = administeredBy;
        this.clinicNotes = clinicNotes;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Helper status calculation
    public String getStatus() {
        if (nextDueDate == null) {
            return "COMPLETED";
        }
        LocalDate today = LocalDate.now();
        if (nextDueDate.isBefore(today)) {
            return "OVERDUE";
        } else if (nextDueDate.isBefore(today.plusDays(30))) {
            return "DUE_SOON";
        }
        return "UP_TO_DATE";
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

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public LocalDate getAdministeredDate() {
        return administeredDate;
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

    public User getAdministeredBy() {
        return administeredBy;
    }

    public void setAdministeredBy(User administeredBy) {
        this.administeredBy = administeredBy;
    }

    public String getClinicNotes() {
        return clinicNotes;
    }

    public void setClinicNotes(String clinicNotes) {
        this.clinicNotes = clinicNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

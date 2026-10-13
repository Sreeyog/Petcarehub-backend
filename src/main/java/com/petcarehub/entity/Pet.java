package com.petcarehub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 50)
    private String species;

    @Column(length = 80)
    private String breed;

    private LocalDate dateOfBirth;

    @Column(length = 30)
    private String gender;

    private Double weightKg;

    @Column(length = 50)
    private String color;

    @Column(length = 60)
    private String microchipNumber;

    @Column(length = 500)
    private String allergies;

    @Column(length = 1000)
    private String specialNotes;

    @Column(length = 500)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties({"password", "createdAt"})
    private User owner;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Pet() {
    }

    public Pet(String name, String species, String breed, LocalDate dateOfBirth, String gender, Double weightKg, String color, String microchipNumber, String allergies, String specialNotes, String photoUrl, User owner) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weightKg = weightKg;
        this.color = color;
        this.microchipNumber = microchipNumber;
        this.allergies = allergies;
        this.specialNotes = specialNotes;
        this.photoUrl = photoUrl;
        this.owner = owner;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

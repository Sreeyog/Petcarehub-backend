package com.petcarehub.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AppointmentRequest {

    @NotNull(message = "Pet ID is required")
    private Long petId;

    @NotNull(message = "Provider/Vet ID is required")
    private Long providerId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Appointment date and time is required")
    private LocalDateTime appointmentDate;

    private String notes;

    public AppointmentRequest() {
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

package com.petcarehub.dto;

public class DashboardStatsDto {
    private long totalPets;
    private long totalAppointments;
    private long pendingAppointments;
    private long completedAppointments;
    private long totalMedicalRecords;
    private long totalVaccinations;
    private long totalUsers;
    private long totalVeterinarians;
    private long totalServiceProviders;

    public DashboardStatsDto() {
    }

    public long getTotalPets() {
        return totalPets;
    }

    public void setTotalPets(long totalPets) {
        this.totalPets = totalPets;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getPendingAppointments() {
        return pendingAppointments;
    }

    public void setPendingAppointments(long pendingAppointments) {
        this.pendingAppointments = pendingAppointments;
    }

    public long getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(long completedAppointments) {
        this.completedAppointments = completedAppointments;
    }

    public long getTotalMedicalRecords() {
        return totalMedicalRecords;
    }

    public void setTotalMedicalRecords(long totalMedicalRecords) {
        this.totalMedicalRecords = totalMedicalRecords;
    }

    public long getTotalVaccinations() {
        return totalVaccinations;
    }

    public void setTotalVaccinations(long totalVaccinations) {
        this.totalVaccinations = totalVaccinations;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalVeterinarians() {
        return totalVeterinarians;
    }

    public void setTotalVeterinarians(long totalVeterinarians) {
        this.totalVeterinarians = totalVeterinarians;
    }

    public long getTotalServiceProviders() {
        return totalServiceProviders;
    }

    public void setTotalServiceProviders(long totalServiceProviders) {
        this.totalServiceProviders = totalServiceProviders;
    }
}

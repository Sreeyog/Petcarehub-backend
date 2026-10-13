package com.petcarehub.dto;

import com.petcarehub.entity.Role;
import com.petcarehub.entity.User;

public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private Role role;
    private String address;
    private String clinicOrBusinessName;
    private String specialization;

    public UserDto() {
    }

    public UserDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.fullName = user.getFullName();
            this.phone = user.getPhone();
            this.role = user.getRole();
            this.address = user.getAddress();
            this.clinicOrBusinessName = user.getClinicOrBusinessName();
            this.specialization = user.getSpecialization();
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClinicOrBusinessName() {
        return clinicOrBusinessName;
    }

    public void setClinicOrBusinessName(String clinicOrBusinessName) {
        this.clinicOrBusinessName = clinicOrBusinessName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

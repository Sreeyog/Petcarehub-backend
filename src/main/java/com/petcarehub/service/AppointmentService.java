package com.petcarehub.service;

import com.petcarehub.dto.AppointmentRequest;
import com.petcarehub.entity.*;
import com.petcarehub.repository.AppointmentRepository;
import com.petcarehub.repository.PetRepository;
import com.petcarehub.repository.ServiceRepository;
import com.petcarehub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final AuthService authService;

    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository, UserRepository userRepository, ServiceRepository serviceRepository, AuthService authService) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.authService = authService;
    }

    @Transactional
    public Appointment createAppointment(AppointmentRequest request) {
        User currentUser = authService.getCurrentAuthenticatedUser();

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + request.getPetId()));

        User provider = userRepository.findById(request.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Provider or Veterinarian not found with id: " + request.getProviderId()));

        ServiceEntity service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + request.getServiceId()));

        // Check ownership if currentUser is PET_OWNER
        if (currentUser.getRole() == Role.ROLE_PET_OWNER && !pet.getOwner().getId().equals(currentUser.getId())) {
            throw new SecurityException("Cannot book appointment for another user's pet");
        }

        Appointment appointment = new Appointment(
                pet,
                pet.getOwner(),
                provider,
                service,
                request.getAppointmentDate(),
                request.getNotes()
        );

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsForCurrentUser() {
        User currentUser = authService.getCurrentAuthenticatedUser();
        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            return appointmentRepository.findAllByOrderByAppointmentDateDesc();
        } else if (currentUser.getRole() == Role.ROLE_VETERINARIAN || currentUser.getRole() == Role.ROLE_SERVICE_PROVIDER) {
            return appointmentRepository.findByProviderIdOrderByAppointmentDateDesc(currentUser.getId());
        } else {
            return appointmentRepository.findByOwnerIdOrderByAppointmentDateDesc(currentUser.getId());
        }
    }

    public List<Appointment> getAppointmentsByPet(Long petId) {
        return appointmentRepository.findByPetIdOrderByAppointmentDateDesc(petId);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
    }

    @Transactional
    public Appointment updateStatus(Long appointmentId, AppointmentStatus status, String providerNotes) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + appointmentId));

        // Validation: Provider or Admin can update status; Owner can cancel their own appointment
        boolean isProvider = appointment.getProvider().getId().equals(currentUser.getId());
        boolean isOwner = appointment.getOwner().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ROLE_ADMIN;

        if (status == AppointmentStatus.CANCELLED) {
            if (!isOwner && !isProvider && !isAdmin) {
                throw new SecurityException("Unauthorized to cancel appointment");
            }
        } else {
            if (!isProvider && !isAdmin) {
                throw new SecurityException("Only the assigned provider or admin can update appointment status to " + status);
            }
        }

        appointment.setStatus(status);
        if (providerNotes != null && !providerNotes.isBlank()) {
            appointment.setProviderNotes(providerNotes);
        }

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAllByOrderByAppointmentDateDesc();
    }
}

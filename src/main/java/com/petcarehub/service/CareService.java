package com.petcarehub.service;

import com.petcarehub.dto.DashboardStatsDto;
import com.petcarehub.dto.ServiceRequest;
import com.petcarehub.dto.UserDto;
import com.petcarehub.entity.AppointmentStatus;
import com.petcarehub.entity.Role;
import com.petcarehub.entity.ServiceCategory;
import com.petcarehub.entity.ServiceEntity;
import com.petcarehub.entity.User;
import com.petcarehub.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final AuthService authService;

    public CareService(ServiceRepository serviceRepository,
                       UserRepository userRepository,
                       PetRepository petRepository,
                       AppointmentRepository appointmentRepository,
                       MedicalRecordRepository medicalRecordRepository,
                       VaccinationRecordRepository vaccinationRecordRepository,
                       AuthService authService) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.vaccinationRecordRepository = vaccinationRecordRepository;
        this.authService = authService;
    }

    public List<ServiceEntity> getAllActiveServices() {
        return serviceRepository.findByActiveTrue();
    }

    public List<ServiceEntity> getServicesByCategory(ServiceCategory category) {
        return serviceRepository.findByCategoryAndActiveTrue(category);
    }

    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + id));
    }

    @Transactional
    public ServiceEntity createService(ServiceRequest request) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_VETERINARIAN && currentUser.getRole() != Role.ROLE_SERVICE_PROVIDER) {
            throw new SecurityException("Unauthorized to create service");
        }

        User provider = null;
        if (request.getProviderId() != null) {
            provider = userRepository.findById(request.getProviderId()).orElse(null);
        } else if (currentUser.getRole() == Role.ROLE_VETERINARIAN || currentUser.getRole() == Role.ROLE_SERVICE_PROVIDER) {
            provider = currentUser;
        }

        ServiceEntity service = new ServiceEntity(
                request.getName(),
                request.getCategory(),
                request.getDescription(),
                request.getPrice(),
                request.getDurationMinutes(),
                provider
        );

        return serviceRepository.save(service);
    }

    @Transactional
    public ServiceEntity updateService(Long id, ServiceRequest request) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + id));

        service.setName(request.getName());
        service.setCategory(request.getCategory());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        if (request.getDurationMinutes() != null) {
            service.setDurationMinutes(request.getDurationMinutes());
        }

        return serviceRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + id));
        service.setActive(false);
        serviceRepository.save(service);
    }

    public List<UserDto> getAllCareProviders() {
        List<User> vets = userRepository.findByRole(Role.ROLE_VETERINARIAN);
        List<User> providers = userRepository.findByRole(Role.ROLE_SERVICE_PROVIDER);

        List<User> all = new ArrayList<>();
        all.addAll(vets);
        all.addAll(providers);

        return all.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public DashboardStatsDto getDashboardStats() {
        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setTotalPets(petRepository.count());
        stats.setTotalAppointments(appointmentRepository.count());
        stats.setPendingAppointments(appointmentRepository.findByStatus(AppointmentStatus.PENDING).size());
        stats.setCompletedAppointments(appointmentRepository.findByStatus(AppointmentStatus.COMPLETED).size());
        stats.setTotalMedicalRecords(medicalRecordRepository.count());
        stats.setTotalVaccinations(vaccinationRecordRepository.count());
        stats.setTotalUsers(userRepository.count());
        stats.setTotalVeterinarians(userRepository.findByRole(Role.ROLE_VETERINARIAN).size());
        stats.setTotalServiceProviders(userRepository.findByRole(Role.ROLE_SERVICE_PROVIDER).size());
        return stats;
    }
}

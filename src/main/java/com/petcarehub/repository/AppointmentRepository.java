package com.petcarehub.repository;

import com.petcarehub.entity.Appointment;
import com.petcarehub.entity.AppointmentStatus;
import com.petcarehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByOwner(User owner);
    List<Appointment> findByOwnerIdOrderByAppointmentDateDesc(Long ownerId);
    List<Appointment> findByProvider(User provider);
    List<Appointment> findByProviderIdOrderByAppointmentDateDesc(Long providerId);
    List<Appointment> findByPetIdOrderByAppointmentDateDesc(Long petId);
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findAllByOrderByAppointmentDateDesc();
}

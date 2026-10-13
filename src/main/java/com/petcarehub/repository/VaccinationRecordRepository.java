package com.petcarehub.repository;

import com.petcarehub.entity.Pet;
import com.petcarehub.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {
    List<VaccinationRecord> findByPetOrderByAdministeredDateDesc(Pet pet);
    List<VaccinationRecord> findByPetIdOrderByAdministeredDateDesc(Long petId);
    List<VaccinationRecord> findByNextDueDateBefore(LocalDate date);
}

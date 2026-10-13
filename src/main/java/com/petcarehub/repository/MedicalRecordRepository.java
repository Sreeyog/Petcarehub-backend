package com.petcarehub.repository;

import com.petcarehub.entity.MedicalRecord;
import com.petcarehub.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPetOrderByRecordDateDesc(Pet pet);
    List<MedicalRecord> findByPetIdOrderByRecordDateDesc(Long petId);
    List<MedicalRecord> findByVeterinarianIdOrderByRecordDateDesc(Long vetId);
    List<MedicalRecord> findAllByOrderByRecordDateDesc();
}

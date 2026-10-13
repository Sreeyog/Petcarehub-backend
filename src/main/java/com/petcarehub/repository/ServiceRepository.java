package com.petcarehub.repository;

import com.petcarehub.entity.ServiceCategory;
import com.petcarehub.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByActiveTrue();
    List<ServiceEntity> findByCategoryAndActiveTrue(ServiceCategory category);
    List<ServiceEntity> findByProviderIdAndActiveTrue(Long providerId);
}

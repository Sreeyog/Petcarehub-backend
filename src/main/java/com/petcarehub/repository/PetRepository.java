package com.petcarehub.repository;

import com.petcarehub.entity.Pet;
import com.petcarehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner(User owner);
    List<Pet> findByOwnerId(Long ownerId);
}

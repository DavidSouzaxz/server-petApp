package com.project.pettvaccine_api.repositories;

import com.project.pettvaccine_api.entity.VaccinesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaccinesRepository extends JpaRepository<VaccinesEntity, UUID> {
    Optional<VaccinesEntity> findByName(String name);
    List<VaccinesEntity> findByPetId(UUID petId);


}

package com.project.pettvaccine_api.repositories;

import com.project.pettvaccine_api.entity.PetsEntity;
import com.project.pettvaccine_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<PetsEntity, UUID> {

   List<PetsEntity> findByBreed(String breed);
   List<PetsEntity> findByBirthDate(LocalDate date);

    List<PetsEntity> findByUserId(UUID id);


}

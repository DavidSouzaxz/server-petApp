package com.project.pettvaccine_api.repositories;

import com.project.pettvaccine_api.entity.OccurrenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OccurrenceRepository extends JpaRepository<OccurrenceEntity, UUID> {
    // Busca todas as ocorrências de um pet ordenadas pela data mais recente
    List<OccurrenceEntity> findByPetIdOrderByOccurrenceDateDesc(UUID petId);
}
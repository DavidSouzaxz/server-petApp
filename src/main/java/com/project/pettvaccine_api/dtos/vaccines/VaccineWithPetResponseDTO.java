package com.project.pettvaccine_api.dtos.vaccines;

import com.project.pettvaccine_api.dtos.pets.PetSummaryDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record VaccineWithPetResponseDTO(
        UUID id,
        String name,
        LocalDateTime applicationDate,
        Boolean isApplied,
        String observations,
        PetSummaryDTO pet // Inclui apenas o básico do Pet aqui
) {}

// Um DTO "leve" só para identificação

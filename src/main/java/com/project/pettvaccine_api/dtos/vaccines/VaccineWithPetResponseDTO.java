package com.project.pettvaccine_api.dtos.vaccines;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.pettvaccine_api.dtos.pets.PetSummaryDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record VaccineWithPetResponseDTO(
        UUID id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime applicationDate,
        Boolean isApplied,
        String observations,
        PetSummaryDTO pet
) {}

// Um DTO "leve" só para identificação

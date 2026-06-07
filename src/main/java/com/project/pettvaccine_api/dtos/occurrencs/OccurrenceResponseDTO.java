package com.project.pettvaccine_api.dtos.occurrencs;

import com.project.pettvaccine_api.models.OccurrenceType;
import java.time.LocalDateTime;
import java.util.UUID;

public record OccurrenceResponseDTO(
    UUID id,
    UUID petId, // Retorna apenas o ID para o front-end mapear
    OccurrenceType type,
    String title,
    String description,
    String photoUrl,
    LocalDateTime occurrenceDate,
    LocalDateTime createdAt
) {}
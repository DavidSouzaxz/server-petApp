package com.project.pettvaccine_api.dtos.vaccines;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record VaccineResponseDTO(
    UUID id,
    String name, 
    LocalDateTime applicationDate,
    Boolean isApplied, 
    String observations
) {}
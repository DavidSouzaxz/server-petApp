package com.project.pettvaccine_api.dtos.vaccines;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record VaccineRequestDTO(
        String name,
        LocalDateTime applicationDate,
        LocalDateTime nextApplicationDate,
        Boolean isApplied,
        String observations,
        UUID petId
) {}
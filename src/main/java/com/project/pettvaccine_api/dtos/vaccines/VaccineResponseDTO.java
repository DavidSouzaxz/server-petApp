package com.project.pettvaccine_api.dtos.vaccines;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record VaccineResponseDTO(
    UUID id,
    String name,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime applicationDate,
    Boolean isApplied, 
    String observations

) {}
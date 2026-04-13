package com.project.pettvaccine_api.dtos.vaccines;

import java.time.LocalDate;
import java.util.UUID;

public record VaccineRequestDTO(String name, LocalDate applicationDate, Boolean isApplied, String observations, UUID petId) {}
package com.project.pettvaccine_api.dtos.pets;

import java.time.LocalDate;
import java.util.UUID;

public record PetRequestDTO(String name, String breed, LocalDate birthDate, UUID userId) {}
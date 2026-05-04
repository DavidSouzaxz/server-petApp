package com.project.pettvaccine_api.dtos.pets;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record PetRequestDTO(
        String name,
        String photoUrl,
        String specie,
        String breed,
        String color,
        LocalDate birthDate,
        Optional<String> microchip,
        Float weight,
        String sex,
        String observations,
        UUID userId
) {}
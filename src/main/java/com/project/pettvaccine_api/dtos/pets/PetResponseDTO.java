package com.project.pettvaccine_api.dtos.pets;

import com.project.pettvaccine_api.dtos.vaccines.VaccineResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PetResponseDTO(
        UUID id,
        String name,
        String breed,
        LocalDate birthDate,
        List<VaccineResponseDTO> vaccines
) {}
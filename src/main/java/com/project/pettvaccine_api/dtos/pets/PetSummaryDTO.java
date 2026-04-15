package com.project.pettvaccine_api.dtos.pets;

import java.util.UUID;

public record PetSummaryDTO(UUID id, String name, String breed) {}
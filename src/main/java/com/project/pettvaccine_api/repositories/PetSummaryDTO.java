package com.project.pettvaccine_api.repositories;

import java.util.UUID;

public record PetSummaryDTO(UUID id, String name, String breed) {}
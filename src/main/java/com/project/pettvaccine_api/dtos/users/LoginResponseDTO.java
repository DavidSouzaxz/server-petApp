package com.project.pettvaccine_api.dtos.users;

import java.util.UUID;

public record LoginResponseDTO(String token, UUID userId, String name) {}
package com.project.pettvaccine_api.dtos.users;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name,String contact, String email) {}
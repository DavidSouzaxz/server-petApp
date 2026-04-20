package com.project.pettvaccine_api.dtos.clinic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Element(Long id, double lat, double lon, Map<String, String> tags) {}
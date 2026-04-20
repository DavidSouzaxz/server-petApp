package com.project.pettvaccine_api.dtos.clinic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OverpassResponse(List<Element> elements) {}
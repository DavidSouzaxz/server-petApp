package com.project.pettvaccine_api.controllers;

import com.project.pettvaccine_api.dtos.occurrencs.OccurrenceResponseDTO;
import com.project.pettvaccine_api.entity.OccurrenceEntity;
import com.project.pettvaccine_api.services.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/occurrences")
public class OccurrenceController {

    @Autowired
    private OccurrenceService service;

    @PostMapping
    public ResponseEntity<OccurrenceEntity> create(@RequestBody OccurrenceEntity occurrence) {
        return ResponseEntity.ok(service.create(occurrence));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> listById(@PathVariable UUID id) {
        return service.listById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<OccurrenceResponseDTO>> listByPet(@PathVariable UUID petId) {
        return ResponseEntity.ok(service.listByPet(petId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OccurrenceEntity> update(@PathVariable UUID id, @RequestBody OccurrenceEntity occurrence) {
        return ResponseEntity.ok(service.update(id, occurrence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
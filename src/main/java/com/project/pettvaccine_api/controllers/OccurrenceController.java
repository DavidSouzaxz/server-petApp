package com.project.pettvaccine_api.controllers;

import com.project.pettvaccine_api.entity.OccurrenceEntity;
import com.project.pettvaccine_api.services.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<OccurrenceEntity>> listByPet(@PathVariable UUID petId) {
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
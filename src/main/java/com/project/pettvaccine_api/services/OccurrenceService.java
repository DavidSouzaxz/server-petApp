package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.entity.OccurrenceEntity;
import com.project.pettvaccine_api.repositories.OccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class OccurrenceService {

    @Autowired
    private OccurrenceRepository repository;

    public OccurrenceEntity create(OccurrenceEntity occurrence) {
        return repository.save(occurrence);
    }

    public List<OccurrenceEntity> listByPet(UUID petId) {
        return repository.findByPetIdOrderByOccurrenceDateDesc(petId);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public OccurrenceEntity update(UUID id, OccurrenceEntity data) {
        OccurrenceEntity current = repository.findById(id).orElseThrow();
        current.setTitle(data.getTitle());
        current.setDescription(data.getDescription());
        current.setType(data.getType());
        current.setOccurrenceDate(data.getOccurrenceDate());
        if (data.getPhotoUrl() != null) current.setPhotoUrl(data.getPhotoUrl());
        return repository.save(current);
    }
}
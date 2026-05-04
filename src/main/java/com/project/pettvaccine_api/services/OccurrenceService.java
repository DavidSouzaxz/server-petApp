package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.entity.OccurrenceEntity;
import com.project.pettvaccine_api.repositories.OccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OccurrenceService {

    @Autowired
    private OccurrenceRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public OccurrenceEntity create(OccurrenceEntity occurrence) {
        return repository.save(occurrence);
    }

    public List<OccurrenceEntity> listByPet(UUID petId) {
        return repository.findByPetIdOrderByOccurrenceDateDesc(petId);
    }
    public Optional<OccurrenceEntity> listById(UUID id) {
        return repository.findById(id);
    }

    public void delete(UUID id) {
        OccurrenceEntity occurrence = repository.findById(id).orElse(null);

        if(occurrence.getPhotoUrl() != null && !occurrence.getPhotoUrl().isEmpty()) {
            cloudinaryService.deleteImage(occurrence.getPhotoUrl());
        }
        repository.deleteById(id);
    }

    public OccurrenceEntity update(UUID id, OccurrenceEntity data) {
        OccurrenceEntity current = repository.findById(id).orElseThrow();
        current.setTitle(data.getTitle());
        current.setDescription(data.getDescription());
        current.setType(data.getType());
        current.setOccurrenceDate(data.getOccurrenceDate());
        if (current.getPhotoUrl() != null && !current.getPhotoUrl().equals(data.getPhotoUrl())) {
            cloudinaryService.deleteImage(current.getPhotoUrl());
        }
        if(data.getPhotoUrl() != null){
            current.setPhotoUrl(data.getPhotoUrl());

        }
        return repository.save(current);
    }
}
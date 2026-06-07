package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.dtos.occurrencs.OccurrenceResponseDTO;
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

    public List<OccurrenceResponseDTO> listByPet(UUID petId) {
        List<OccurrenceEntity> occurrences = repository.findByPetIdOrderByOccurrenceDateDesc(petId);
        return occurrences.stream()
                .map(occ -> new OccurrenceResponseDTO(
                        occ.getId(),
                        occ.getPet().getId(), // 👈 Pega apenas o ID do relacionamento de objetos
                        occ.getType(),
                        occ.getTitle(),
                        occ.getDescription(),
                        occ.getPhotoUrl(),
                        occ.getOccurrenceDate(),
                        occ.getCreatedAt()
                ))
                .toList();
    }

    public Optional<OccurrenceResponseDTO> listById(UUID id) {
        return repository.findById(id)
                .map(occ -> new OccurrenceResponseDTO(
                        occ.getId(),
                        occ.getPet().getId(), // 👈 Evita o loop trazendo apenas o ID do pet
                        occ.getType(),
                        occ.getTitle(),
                        occ.getDescription(),
                        occ.getPhotoUrl(),
                        occ.getOccurrenceDate(),
                        occ.getCreatedAt()
                ));
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
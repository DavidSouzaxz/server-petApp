package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.dtos.vaccines.VaccineRequestDTO;
import com.project.pettvaccine_api.entity.PetsEntity;
import com.project.pettvaccine_api.entity.VaccinesEntity;
import com.project.pettvaccine_api.repositories.PetRepository;
import com.project.pettvaccine_api.dtos.pets.PetSummaryDTO;
import com.project.pettvaccine_api.dtos.vaccines.VaccineWithPetResponseDTO;
import com.project.pettvaccine_api.repositories.VaccinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VaccineService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private VaccinesRepository vaccineRepository;


    public List<VaccineWithPetResponseDTO> listVaccines(){
        return vaccineRepository.findAll().stream().map(vaccines -> new VaccineWithPetResponseDTO(
                vaccines.getId(),
                vaccines.getName(),
                vaccines.getApplicationDate(),
                vaccines.getIsApplied(),
                vaccines.getObservations(),
                new PetSummaryDTO(vaccines.getPet().getId(), vaccines.getPet().getName(), vaccines.getPet().getBreed())
        )).toList();
    }

    public VaccineWithPetResponseDTO listVaccinesById(UUID id) {
        VaccinesEntity vaccine = vaccineRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        return new VaccineWithPetResponseDTO(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getApplicationDate(),
                vaccine.getIsApplied(),
                vaccine.getObservations(),
                new PetSummaryDTO(vaccine.getPet().getId(), vaccine.getPet().getName(), vaccine.getPet().getBreed())
        );
    }

    public VaccineWithPetResponseDTO registerVaccine(VaccineRequestDTO dto) {

        PetsEntity pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado para vincular a vacina"));


        VaccinesEntity vaccine = new VaccinesEntity();
        vaccine.setName(dto.name());
        vaccine.setApplicationDate(dto.applicationDate());
        vaccine.setIsApplied(dto.isApplied());
        vaccine.setObservations(dto.observations());


        vaccine.setPet(pet);


        VaccinesEntity salva = vaccineRepository.save(vaccine);


        return new VaccineWithPetResponseDTO(
                salva.getId(),
                salva.getName(),
                salva.getApplicationDate(),
                salva.getIsApplied(),
                salva.getObservations(),
                new PetSummaryDTO(vaccine.getPet().getId(), vaccine.getPet().getName(), vaccine.getPet().getBreed())
        );
    }

    public String vaccineIsApplied(UUID id) {
        VaccinesEntity vaccine = vaccineRepository.findById(id).orElseThrow(() -> new RuntimeException("Vacina não encontrada"));
        String response;
        if(vaccine.getIsApplied()){
            response = "Vacina já foi aplicada";
        }else{
            vaccine.setIsApplied(true);
            response = "Vacina aplicada com sucesso";
        }

        return response;
    }

    public void deleteVaccine(UUID id) {
        vaccineRepository.deleteById(id);
    }
}

package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.dtos.pets.PetRequestDTO;
import com.project.pettvaccine_api.dtos.pets.PetResponseDTO;
import com.project.pettvaccine_api.dtos.vaccines.VaccineResponseDTO;
import com.project.pettvaccine_api.entity.PetsEntity;
import com.project.pettvaccine_api.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<PetResponseDTO> listarPets() {
        List<PetsEntity> lista = petRepository.findAll();

        return lista.stream()
                .map(pet -> new PetResponseDTO(
                        pet.getId(),
                        pet.getName(),
                        pet.getBreed(),
                        pet.getBirthDate(),
                        pet.getListVaccines().stream().map(
                                vaccine -> new VaccineResponseDTO(
                                        vaccine.getId(),
                                        vaccine.getName(),
                                        vaccine.getApplicationDate(),
                                        vaccine.getIsApplied(),
                                        vaccine.getObservations()
                                )
                        ).toList()
                )).toList();
    }

    public PetResponseDTO buscarPetPorId(UUID id) {
        PetsEntity pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getListVaccines().stream().map(
                        vaccine -> new VaccineResponseDTO(
                                vaccine.getId(),
                                vaccine.getName(),
                                vaccine.getApplicationDate(),
                                vaccine.getIsApplied(),
                                vaccine.getObservations()
                        )
                ).toList()
        );
    }


}
package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.dtos.pets.PetRequestDTO;
import com.project.pettvaccine_api.dtos.pets.PetResponseDTO;
import com.project.pettvaccine_api.dtos.vaccines.VaccineResponseDTO;
import com.project.pettvaccine_api.entity.PetsEntity;
import com.project.pettvaccine_api.entity.UserEntity;
import com.project.pettvaccine_api.repositories.PetRepository;
import com.project.pettvaccine_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

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


    public PetResponseDTO cadastrarPet(PetRequestDTO petRequestDTO) {
        UserEntity user = userRepository.findById(petRequestDTO.userId()).orElseThrow(() -> new RuntimeException("User não encontrado"));

        PetsEntity pet = new PetsEntity();
        pet.setUser(user);
        pet.setName(petRequestDTO.name());
        pet.setBreed(petRequestDTO.breed());
        pet.setBirthDate(petRequestDTO.birthDate());
        pet.setListVaccines(new ArrayList<>());

        PetsEntity petEntity = petRepository.save(pet);
        return new PetResponseDTO(
                petEntity.getId(),
                petEntity.getName(),
                petEntity.getBreed(),
                petEntity.getBirthDate(),
                petEntity.getListVaccines().stream().map(vaccine -> new VaccineResponseDTO(
                        vaccine.getId(),
                        vaccine.getName(),
                        vaccine.getApplicationDate(),
                        vaccine.getIsApplied(),
                        vaccine.getObservations()
                )).toList()
        );

    }


    public PetResponseDTO atualizarPet(PetRequestDTO petRequestDTO, UUID id) {
        PetsEntity petUpdate = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        petUpdate.setName(petRequestDTO.name());
        petUpdate.setBreed(petRequestDTO.breed());
        petUpdate.setBirthDate(petRequestDTO.birthDate());

        PetsEntity pet = petRepository.save(petUpdate);

        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getListVaccines().stream().map(vaccine -> new VaccineResponseDTO(
                    vaccine.getId(),
                    vaccine.getName(),
                    vaccine.getApplicationDate(),
                    vaccine.getIsApplied(),
                    vaccine.getObservations()
                 )).toList()
        );
    }

    public void deletarPet(UUID id) {
        petRepository.deleteById(id);
    }

}
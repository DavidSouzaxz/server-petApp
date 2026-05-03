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

    public List<PetResponseDTO> listPets() {
        List<PetsEntity> lista = petRepository.findAll();

        return lista.stream()
                .map(pet -> new PetResponseDTO(
                        pet.getId(),
                        pet.getName(),
                        pet.getBreed(),
                        pet.getSpecie(),
                        pet.getColor(),
                        pet.getMicrochip(),
                        pet.getObservations(),
                        pet.getWeight(),
                        pet.getSex(),
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

    public PetResponseDTO findPetById(UUID id) {
        PetsEntity pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getBreed(),
                pet.getSpecie(),
                pet.getColor(),
                pet.getMicrochip(),
                pet.getObservations(),
                pet.getWeight(),
                pet.getSex(),
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

    public List<PetResponseDTO> listPetsByUserId(UUID id) {
        List<PetsEntity> pets = petRepository.findByUserId(id);

        return pets.stream()
                .map(pet -> new PetResponseDTO(
                        pet.getId(),
                        pet.getName(),
                        pet.getBreed(),
                        pet.getSpecie(),
                        pet.getColor(),
                        pet.getMicrochip(),
                        pet.getObservations(),
                        pet.getWeight(),
                        pet.getSex(),
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


    public PetResponseDTO registerPet(PetRequestDTO petRequestDTO) {
        UserEntity user = userRepository.findById(petRequestDTO.userId()).orElseThrow(() -> new RuntimeException("User não encontrado"));

        PetsEntity pet = new PetsEntity();
        pet.setUser(user);
        pet.setName(petRequestDTO.name());
        pet.setSpecie(petRequestDTO.specie());
        pet.setBreed(petRequestDTO.breed());
        pet.setColor(petRequestDTO.color());
        pet.setBirthDate(petRequestDTO.birthDate());
        petRequestDTO.microchip().ifPresent(pet::setMicrochip);
        pet.setWeight(petRequestDTO.weight());
        pet.setSex(petRequestDTO.sex());
        pet.setObservations(petRequestDTO.observations());
        pet.setListVaccines(new ArrayList<>());


        PetsEntity petEntity = petRepository.save(pet);
        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getBreed(),
                pet.getSpecie(),
                pet.getColor(),
                pet.getMicrochip(),
                pet.getObservations(),
                pet.getWeight(),
                pet.getSex(),
                pet.getBirthDate(),
                petEntity.getListVaccines().stream().map(vaccine -> new VaccineResponseDTO(
                        vaccine.getId(),
                        vaccine.getName(),
                        vaccine.getApplicationDate(),
                        vaccine.getIsApplied(),
                        vaccine.getObservations()
                )).toList()
        );

    }


    public PetResponseDTO updatePet(PetRequestDTO petRequestDTO, UUID id) {
        PetsEntity petUpdate = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        petUpdate.setName(petRequestDTO.name());
        petUpdate.setSpecie(petRequestDTO.specie());
        petUpdate.setBreed(petRequestDTO.breed());
        petUpdate.setColor(petRequestDTO.color());
        petUpdate.setBirthDate(petRequestDTO.birthDate());

        petRequestDTO.microchip().ifPresent(petUpdate::setMicrochip);
        petUpdate.setWeight(petRequestDTO.weight());
        petUpdate.setSex(petRequestDTO.sex());
        petUpdate.setObservations(petRequestDTO.observations());

        PetsEntity pet = petRepository.save(petUpdate);

        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getBreed(),
                pet.getSpecie(),
                pet.getColor(),
                pet.getMicrochip(),
                pet.getObservations(),
                pet.getWeight(),
                pet.getSex(),
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

    public void deletePet(UUID id) {
        petRepository.deleteById(id);
    }

}
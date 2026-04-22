package com.project.pettvaccine_api.controllers;

import com.project.pettvaccine_api.dtos.pets.PetRequestDTO;
import com.project.pettvaccine_api.dtos.pets.PetResponseDTO;
import com.project.pettvaccine_api.entity.PetsEntity;
import com.project.pettvaccine_api.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listPets() {
        List<PetResponseDTO> lista = petService.listPets();

        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> getPetById(@PathVariable UUID id) {

        PetResponseDTO pet = petService.findPetById(id);
        return ResponseEntity.ok().body(pet);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<PetResponseDTO>> getPetsByUserId(@PathVariable UUID userId) {
        List<PetResponseDTO> pets = petService.listPetsByUserId(userId);

        return ResponseEntity.ok(pets);
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> createPet(@RequestBody @Valid PetRequestDTO petRequestDTO) {
        PetResponseDTO petRegister = petService.registerPet(petRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(petRegister);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updatePet(@PathVariable UUID id, @RequestBody @Valid PetRequestDTO petRequestDTO) {
        PetResponseDTO petUpdate = petService.updatePet(petRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(petUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable UUID id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}

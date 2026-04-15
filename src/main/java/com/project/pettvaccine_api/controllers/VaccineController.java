package com.project.pettvaccine_api.controllers;


import com.project.pettvaccine_api.dtos.vaccines.VaccineRequestDTO;
import com.project.pettvaccine_api.dtos.vaccines.VaccineWithPetResponseDTO;
import com.project.pettvaccine_api.services.VaccineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vacinas")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<VaccineWithPetResponseDTO>> listVaccines() {
        List<VaccineWithPetResponseDTO> listVaccine = vaccineService.listVaccines();

        return ResponseEntity.ok().body(listVaccine);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineWithPetResponseDTO> getVaccine(@PathVariable UUID id) {
        VaccineWithPetResponseDTO vaccine = vaccineService.listVaccinesById(id);

        return ResponseEntity.ok().body(vaccine);
    }

    @PostMapping
    public ResponseEntity<VaccineWithPetResponseDTO> registerVaccine(@RequestBody @Valid VaccineRequestDTO vaccineRequestDTO) {
        VaccineWithPetResponseDTO vaccine = vaccineService.registerVaccine(vaccineRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaccine);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable UUID id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.noContent().build();
    }
}

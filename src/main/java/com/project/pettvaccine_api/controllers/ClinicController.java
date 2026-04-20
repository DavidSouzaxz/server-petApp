package com.project.pettvaccine_api.controllers;

import com.project.pettvaccine_api.dtos.clinic.ClinicDTO;
import com.project.pettvaccine_api.services.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {
    @Autowired
    private ClinicService clinicService;

    @GetMapping("/nearby")
    public ResponseEntity<List<ClinicDTO>> getNearby(@RequestParam double lat, @RequestParam double lng) {
        return ResponseEntity.ok(clinicService.findNearbyClinics(lat, lng));
    }
}
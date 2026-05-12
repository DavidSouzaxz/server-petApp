package com.project.pettvaccine_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sys")
public class HealthController {

    @GetMapping("/sync")
    public ResponseEntity<Void> heartbeat() {
        return ResponseEntity.ok().build();
    }
}

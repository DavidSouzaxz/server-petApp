package com.project.pettvaccine_api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sys")
public class HealthController {
    @Value("${HEALTH_KEY}") 
    private String secretKey;

    @GetMapping("/sync")
    public ResponseEntity<Void> heartbeat(@RequestHeader("X-Health-Key") String receivedKey) {
        if (secretKey.equals(receivedKey)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

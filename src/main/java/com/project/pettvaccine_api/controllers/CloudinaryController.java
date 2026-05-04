package com.project.pettvaccine_api.controllers;

import com.cloudinary.Cloudinary;
import com.project.pettvaccine_api.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/signature")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUploadSignature() {
        return ResponseEntity.ok(cloudinaryService.generateSignature());
    }

}

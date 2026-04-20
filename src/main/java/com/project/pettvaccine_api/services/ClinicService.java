package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.dtos.clinic.ClinicDTO;
import org.springframework.beans.factory.annotation.Value; // IMPORTANTE: Corrigido
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders; // IMPORTANTE: Corrigido
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ClinicService {

    @Value("${google.places.api.key}")
    private String apiKey;

    @Value("${google.places.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ClinicDTO> findNearbyClinics(double lat, double lon) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", apiKey);

        headers.set("X-Goog-FieldMask", "places.name,places.displayName,places.formattedAddress,places.location,places.internationalPhoneNumber");

        Map<String, Object> body = Map.of(
                "includedTypes", List.of("veterinary_care"),
                "maxResultCount", 10,
                "locationRestriction", Map.of(
                        "circle", Map.of(
                                "center", Map.of("latitude", lat, "longitude", lon),
                                "radius", 5000
                        )
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);


        var response = restTemplate.postForObject(url, entity, Map.class);

        return tratarRespostaGoogle(response);
    }

    private List<ClinicDTO> tratarRespostaGoogle(Map<String, Object> response) {
        if (response == null || !response.containsKey("places")) {
            return Collections.emptyList();
        }

        List<ClinicDTO> clinicas = new ArrayList<>();
        List<Map<String, Object>> places = (List<Map<String, Object>>) response.get("places");

        for (Map<String, Object> place : places) {

            String id = (String) place.get("name");

            Map<String, String> displayName = (Map<String, String>) place.get("displayName");
            String nome = (displayName != null) ? displayName.get("text") : "Clínica Sem Nome";


            Map<String, Double> location = (Map<String, Double>) place.get("location");
            double lat = (location != null) ? location.get("latitude") : 0.0;
            double lng = (location != null) ? location.get("longitude") : 0.0;

            String endereco = (String) place.get("formattedAddress");
            String telefone = (String) place.get("internationalPhoneNumber");


            clinicas.add(new ClinicDTO(id, nome, lat, lng, telefone, endereco));
        }

        return clinicas;
    }
}
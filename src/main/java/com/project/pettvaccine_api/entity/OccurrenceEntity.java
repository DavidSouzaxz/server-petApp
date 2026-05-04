package com.project.pettvaccine_api.entity;

import com.project.pettvaccine_api.models.OccurrenceType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "occurrences")
@Data
public class OccurrenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID petId; // Relacionamento com o Pet

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OccurrenceType type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String photoUrl;

    @Column(nullable = false)
    private LocalDateTime occurrenceDate;

    private LocalDateTime createdAt = LocalDateTime.now();
}
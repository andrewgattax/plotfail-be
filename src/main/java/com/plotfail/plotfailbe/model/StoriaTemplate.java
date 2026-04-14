package com.plotfail.plotfailbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoriaTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String titolo;

    @Column(nullable = false)
    private boolean used = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatoGenerazione statoGenerazione;

    @Lob
    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String template;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "template")
    private Set<Storia> storieCreate;

    @Column(nullable = true)
    private CategoriaTemplate categoria;

    @OneToMany(mappedBy = "template")
    private Set<SalvataggioTemplate> salvatoDa;

    private Instant createdAt = Instant.now();

    public boolean equals(StoriaTemplate storiaTemplate) {
        return this.id.equals(storiaTemplate.getId());
    }
}


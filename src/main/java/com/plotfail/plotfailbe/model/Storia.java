package com.plotfail.plotfailbe.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Storia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Lob
    private String contenuto;

    private boolean pubblico;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoriaTemplate template;

    @ManyToOne(fetch = FetchType.LAZY)
    private Utente autore;
}

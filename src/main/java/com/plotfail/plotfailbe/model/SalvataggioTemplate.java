package com.plotfail.plotfailbe.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@Entity
@Data
public class SalvataggioTemplate {
    @EmbeddedId
    private SalvataggioTemplateId id;

    @ManyToOne
    @MapsId("utenteId")
    private Utente user;

    @ManyToOne
    @MapsId("templateId")
    private StoriaTemplate template;

    private Instant savedAt = Instant.now();
}

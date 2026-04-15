package com.plotfail.plotfailbe.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SalvataggioTemplateId {
    private Long utenteId;
    private Long templateId;
}

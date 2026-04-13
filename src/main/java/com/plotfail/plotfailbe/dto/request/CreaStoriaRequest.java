package com.plotfail.plotfailbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreaStoriaRequest {
    @NotBlank(message = "Il titolo non deve essere vuoto")
    private String titolo;
    @NotBlank(message = "Il contenuto non deve essere vuoto")
    private String contenuto;
    private boolean pubblico = false;
    @NotNull(message = "Il templateId non può essere nullo")
    private Long templateId;
}

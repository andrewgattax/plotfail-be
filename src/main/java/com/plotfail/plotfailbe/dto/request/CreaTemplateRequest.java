package com.plotfail.plotfailbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreaTemplateRequest {
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String titolo;
    @NotBlank(message = "Il contenuto non può essere vuoto")
    private String contenuto;
    @NotBlank(message = "Il campo categoria non può essere vuoto")
    private String categoria;
}

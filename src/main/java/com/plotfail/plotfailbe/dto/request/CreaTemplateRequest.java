package com.plotfail.plotfailbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "Create template request")
public class CreaTemplateRequest {
    @NotBlank(message = "Il titolo non può essere vuoto")
    @Schema(description = "Template title", example = "Fantasy Adventure")
    private String titolo;
    @NotBlank(message = "Il contenuto non può essere vuoto")
    @Schema(description = "Template content", example = "A hero's journey...")
    private String contenuto;
    @NotBlank(message = "Il campo categoria non può essere vuoto")
    @Schema(description = "Template category", example = "FANTASY")
    private String categoria;
}

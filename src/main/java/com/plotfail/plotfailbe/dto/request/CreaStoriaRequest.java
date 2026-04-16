package com.plotfail.plotfailbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "Create story request")
public class CreaStoriaRequest {
    @NotBlank(message = "Il titolo non deve essere vuoto")
    @Schema(description = "Story title", example = "My Story")
    private String titolo;
    @NotBlank(message = "Il contenuto non deve essere vuoto")
    @Schema(description = "Story content", example = "Once upon a time...")
    private String contenuto;
    @Schema(description = "Is public", example = "false")
    private boolean pubblico = false;
    @NotNull(message = "Il templateId non può essere nullo")
    @Schema(description = "Template ID", example = "1")
    private Long templateId;
}

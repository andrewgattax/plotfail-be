package com.plotfail.plotfailbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Story response")
public class StoriaResponse {
    @Schema(description = "Story ID", example = "1")
    private Long id;
    @Schema(description = "Story title", example = "My Story")
    private String titolo;
    @Schema(description = "Story author", example = "user123")
    private String autore;
    @Schema(description = "Is public", example = "true")
    private boolean isPubblico;
    @Schema(description = "Story content", example = "Once upon a time...")
    private String contenuto;
    @Schema(description = "Template ID", example = "1")
    private Long templateId;
    @Schema(description = "Story category", example = "FUNNY")
    private String categoria;
}

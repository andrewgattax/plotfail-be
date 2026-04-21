package com.plotfail.plotfailbe.dto.response;

import com.plotfail.plotfailbe.model.StatoGenerazione;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Template response")
public class TemplateResponse {
    @Schema(description = "Template ID", example = "1")
    private Long id;
    @Schema(description = "Template title", example = "Fantasy Adventure")
    private String titolo;
    @Schema(description = "Template content", example = "A hero's journey...")
    private String contenuto;
    @Schema(description = "Template category", example = "FANTASY")
    private String categoria;
    @Schema(description = "Generation status")
    private StatoGenerazione status;
    @Schema(description = "Stories created count", example = "10")
    private int storieCreateCount;
    @Schema(description = "Is used", example = "false")
    private boolean used;
    @Schema(description = "Is saved", example = "true")
    private boolean saved;
}

package com.plotfail.plotfailbe.dto.response;

import com.plotfail.plotfailbe.model.CategoriaTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Generated story response")
public class GeneraStoriaResponse {
    @Schema(description = "Story ID", example = "1")
    private Long id;
    @Schema(description = "Story category")
    private CategoriaTemplate categoria;
    @Schema(description = "Generation status", example = "COMPLETED")
    private String status;
    @Schema(description = "Story title", example = "My Story")
    private String titolo;
    @Schema(description = "Story content", example = "Once upon a time...")
    private String contenuto;
}

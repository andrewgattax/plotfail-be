package com.plotfail.plotfailbe.dto.response;

import com.plotfail.plotfailbe.model.CategoriaTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Compact template response")
public class TemplateCompactResponse {
    @Schema(description = "Template ID", example = "1")
    private Long id;
    @Schema(description = "Template title", example = "Fantasy Adventure")
    private String titolo;
    @Schema(description = "Template preview", example = "A hero's journey...")
    private String preview;
    @Schema(description = "Template category")
    private CategoriaTemplate categoria;
    @Schema(description = "Stories created count", example = "10")
    private int storieCreateCount;
}

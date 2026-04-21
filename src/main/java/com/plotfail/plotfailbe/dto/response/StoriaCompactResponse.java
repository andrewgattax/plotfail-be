package com.plotfail.plotfailbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "Compact story response")
public class StoriaCompactResponse {
    @Schema(description = "Story ID", example = "1")
    private Long id;
    @Schema(description = "Story title", example = "My Story")
    private String titolo;
    @Schema(description = "Story category", example = "FUNNY")
    private String categoria;
    @Schema(description = "Story author", example = "user123")
    private String autore;
    @Schema(description = "Story preview")
    private String preview;
    @Schema(description = "Is public", example = "true")
    private boolean isPublic;
}
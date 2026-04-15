package com.plotfail.plotfailbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "N8N error response")
public class N8NErrorResponse {
    @Schema(description = "Error message", example = "Generation failed")
    private String message;
}

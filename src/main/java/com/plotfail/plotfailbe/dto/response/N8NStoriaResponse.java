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
@Schema(description = "N8N story response")
public class N8NStoriaResponse {
    @Schema(description = "Story title", example = "My Story")
    private String title;
    @Schema(description = "Story content", example = "Once upon a time...")
    private String content;
}

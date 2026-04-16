package com.plotfail.plotfailbe.dto.request;

import com.plotfail.plotfailbe.model.StatoGenerazione;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Story generation notification")
public class NotifyFinishedRequest {
    @Schema(description = "Story title", example = "My Story")
    private String titolo;
    @Schema(description = "Story content", example = "Once upon a time...")
    private String contenuto;
    @Schema(description = "Story ID", example = "1")
    private Long idStoria;
}

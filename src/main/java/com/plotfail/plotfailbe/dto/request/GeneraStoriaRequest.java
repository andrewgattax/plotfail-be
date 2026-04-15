package com.plotfail.plotfailbe.dto.request;

import com.plotfail.plotfailbe.model.CategoriaTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Generate story from template")
public class GeneraStoriaRequest {
    @NotNull(message = "La categoria non può essere vuota")
    @Schema(description = "Story category")
    private CategoriaTemplate categoria;
}

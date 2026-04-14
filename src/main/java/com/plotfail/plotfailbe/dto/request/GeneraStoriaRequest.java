package com.plotfail.plotfailbe.dto.request;

import com.plotfail.plotfailbe.model.CategoriaTemplate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneraStoriaRequest {
    @NotNull(message = "La categoria non può essere vuota")
    private CategoriaTemplate categoria;
}

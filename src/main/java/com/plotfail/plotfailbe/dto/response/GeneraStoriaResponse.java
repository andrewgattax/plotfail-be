package com.plotfail.plotfailbe.dto.response;

import com.plotfail.plotfailbe.model.CategoriaTemplate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneraStoriaResponse {
    private Long id;
    private CategoriaTemplate categoria;
    private String status;
    private String titolo;
    private String contenuto;
}

package com.plotfail.plotfailbe.dto.response;

import com.plotfail.plotfailbe.model.CategoriaTemplate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateCompactResponse {
    private Long id;
    private String titolo;
    private String preview;
    private CategoriaTemplate categoria;
    private int storieCreateCount;
}

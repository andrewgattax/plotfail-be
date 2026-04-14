package com.plotfail.plotfailbe.dto.response;

import com.plotfail.plotfailbe.model.StatoGenerazione;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateResponse {
    private Long id;
    private String titolo;
    private String contenuto;
    private String categoria;
    private StatoGenerazione status;
    private int storieCreateCount;
    private boolean used;
}

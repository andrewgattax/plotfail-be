package com.plotfail.plotfailbe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoriaResponse {
    private Long id;
    private String titolo;
    private String autore;
    private boolean isPubblico;
    private String contenuto;
    private Long templateId;
}

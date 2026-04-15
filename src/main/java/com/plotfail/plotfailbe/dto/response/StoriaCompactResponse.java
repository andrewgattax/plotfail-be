package com.plotfail.plotfailbe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoriaCompactResponse {
    private Long id;
    private String titolo;
    private boolean isPublic;
}
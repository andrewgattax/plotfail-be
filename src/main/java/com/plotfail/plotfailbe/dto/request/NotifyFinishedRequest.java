package com.plotfail.plotfailbe.dto.request;

import com.plotfail.plotfailbe.model.StatoGenerazione;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NotifyFinishedRequest {
    private String titolo;
    private String contenuto;
    private Long idStoria;
}

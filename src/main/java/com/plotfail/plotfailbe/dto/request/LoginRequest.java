package com.plotfail.plotfailbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "Lo username non puo essere vuoto")
    private String username;
    @NotBlank(message= "La password non puo essere vuota")
    private String password;
}

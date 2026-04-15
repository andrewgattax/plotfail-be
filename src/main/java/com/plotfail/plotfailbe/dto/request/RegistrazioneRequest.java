package com.plotfail.plotfailbe.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegistrazioneRequest {
    @NotBlank(message = "Lo username non puo essere vuoto")
    private String username;
    @NotBlank(message = "La password non puo essere vuota")
    private String password;
    @NotBlank(message = "Il campo di verifica password non puo essere vuoto")
    private String verificaPassword;

    @AssertTrue(message = "Le password non corrispondono")
    @JsonIgnore
    public boolean isPasswordValid() {
        return password.equals(verificaPassword);
    }
}

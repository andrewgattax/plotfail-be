package com.plotfail.plotfailbe.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "User registration request")
public class RegistrazioneRequest {
    @NotBlank(message = "Lo username non puo essere vuoto")
    @Schema(description = "Username", example = "user123")
    private String username;
    @NotBlank(message = "La password non puo essere vuota")
    @Schema(description = "Password", example = "password123")
    private String password;
    @NotBlank(message = "Il campo di verifica password non puo essere vuoto")
    @Schema(description = "Password verification", example = "password123")
    private String verificaPassword;

    @AssertTrue(message = "Le password non corrispondono")
    @JsonIgnore
    public boolean isPasswordValid() {
        return password.equals(verificaPassword);
    }
}

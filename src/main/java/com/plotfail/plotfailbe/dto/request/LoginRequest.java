package com.plotfail.plotfailbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "User login request")
public class LoginRequest {
    @NotBlank(message = "Lo username non puo essere vuoto")
    @Schema(description = "Username", example = "user123")
    private String username;
    @NotBlank(message= "La password non puo essere vuota")
    @Schema(description = "Password", example = "password123")
    private String password;
}

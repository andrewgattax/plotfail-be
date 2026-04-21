package com.plotfail.plotfailbe.controller;

import com.plotfail.plotfailbe.dto.request.LoginRequest;
import com.plotfail.plotfailbe.dto.request.RegistrazioneRequest;
import com.plotfail.plotfailbe.dto.response.TokenResponse;
import com.plotfail.plotfailbe.exception.ErrorResponse;
import com.plotfail.plotfailbe.model.Utente;
import com.plotfail.plotfailbe.security.JwtUserPrincipal;
import com.plotfail.plotfailbe.service.UtenteService;
import com.plotfail.plotfailbe.service.UtilitiesService;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utente")
@RequiredArgsConstructor
@Tag(name = "Utente", description = "User management")
public class UtenteController {

    private final UtenteService utenteService;
    private final UtilitiesService utilitiesService;

    @PostMapping("/signup")
    @Operation(summary = "Register user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User registered"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "409", description = "User already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> signup(@Valid @RequestBody RegistrazioneRequest request) {
        utenteService.registraUtente(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User logged in"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Credentials not valid"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = utenteService.login(loginRequest);
        ResponseCookie cookie = ResponseCookie.from("jwt", tokenResponse.getToken())
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(2592000) // Converti da millisecondi a secondi
                .build();
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(tokenResponse);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User logged out"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt")
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0) // Converti da millisecondi a secondi
                .build();
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getMe() {
        JwtUserPrincipal principal = utilitiesService.getJwtUserPrincipal();
        return ResponseEntity.ok(principal.getUsername());
    }

}

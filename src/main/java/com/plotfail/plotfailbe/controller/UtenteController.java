package com.plotfail.plotfailbe.controller;

import com.plotfail.plotfailbe.dto.request.LoginRequest;
import com.plotfail.plotfailbe.dto.request.RegistrazioneRequest;
import com.plotfail.plotfailbe.dto.response.TokenResponse;
import com.plotfail.plotfailbe.model.Utente;
import com.plotfail.plotfailbe.security.JwtUserPrincipal;
import com.plotfail.plotfailbe.service.UtenteService;
import com.plotfail.plotfailbe.service.UtilitiesService;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utente")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final UtilitiesService utilitiesService;

    @PostMapping("/registrazione")
    public ResponseEntity<Void> registrazione(@Valid @RequestBody RegistrazioneRequest request) {
        utenteService.registraUtente(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = utenteService.login(loginRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMe() {
        JwtUserPrincipal principal = utilitiesService.getJwtUserPrincipal();
        return ResponseEntity.ok(principal.getUsername());
    }

}

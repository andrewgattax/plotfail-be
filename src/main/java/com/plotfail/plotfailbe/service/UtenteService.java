package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.dto.request.LoginRequest;
import com.plotfail.plotfailbe.dto.request.RegistrazioneRequest;
import com.plotfail.plotfailbe.dto.response.TokenResponse;
import com.plotfail.plotfailbe.exception.UsernameGiaEsistenteException;
import com.plotfail.plotfailbe.model.Storia;
import com.plotfail.plotfailbe.model.Utente;
import com.plotfail.plotfailbe.repo.UtenteRepo;
import com.plotfail.plotfailbe.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepo utenteRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void registraUtente(RegistrazioneRequest request) {
        Utente nuovoUtente = new Utente();
        nuovoUtente.setUsername(request.getUsername());
        nuovoUtente.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        try {
            utenteRepo.save(nuovoUtente);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameGiaEsistenteException("Username già esistente");
        }

    }

    public TokenResponse login(LoginRequest request) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Credenziali non valide");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Utente utente = utenteRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Credenziali non valide"));
        Integer storiePubblicate = utente.getStorieCreate().stream().filter((Storia::isPubblico)).toList().size();
        Integer templateSalvati = utente.getTemplatesSalvati().size();
        String token = jwtService.generaToken(new HashMap<>() {{
            put("uid", utente.getId());
            put("username", utente.getUsername());
            put("storiePubblicate", storiePubblicate);
            put("templateSalvati", templateSalvati);
            put("storieCondivise", 0); //todo: aggiorna questa merda
        }}, utente);
        return new TokenResponse(token);
    }
}

package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.model.Utente;
import com.plotfail.plotfailbe.repo.UtenteRepo;
import com.plotfail.plotfailbe.security.JwtUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilitiesService {

    private final UtenteRepo utenteRepo;

    public UtilitiesService(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    public JwtUserPrincipal  getJwtUserPrincipal() {
        return (JwtUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Utente getUtente() {
        JwtUserPrincipal principal = getJwtUserPrincipal();
        return utenteRepo.findByUsername(principal.getUsername()).orElseThrow(() -> new RuntimeException("Utente non trovato: " + principal.getUsername()));
    }
}

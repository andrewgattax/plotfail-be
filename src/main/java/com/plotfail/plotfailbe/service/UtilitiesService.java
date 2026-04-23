package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.exception.UnauthorizedException;
import com.plotfail.plotfailbe.model.Utente;
import com.plotfail.plotfailbe.repo.UtenteRepo;
import com.plotfail.plotfailbe.security.JwtUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilitiesService {

    private final UtenteRepo utenteRepo;

    public UtilitiesService(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    public Optional<JwtUserPrincipal> getJwtUserPrincipal() {
        try {
            //todo fixali tu coglione
            return Optional.ofNullable( SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser") ? null : (JwtUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            throw new UnauthorizedException("Utente non autorizzato");
        }
    }

    public Optional<Utente> getUtente() {
        Optional<Utente> user = Optional.empty();
        Optional<JwtUserPrincipal> principal = getJwtUserPrincipal();
        if(principal.isEmpty()) {
            return Optional.empty();
        }
        user = utenteRepo.findByUsername(principal.get().getUsername());
        return user;
    }
}

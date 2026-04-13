package com.plotfail.plotfailbe.repo;

import com.plotfail.plotfailbe.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepo extends JpaRepository<Utente, Long> {
    public Optional<Utente> findByUsername(String username);
}

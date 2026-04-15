package com.plotfail.plotfailbe.repo;

import com.plotfail.plotfailbe.model.Storia;
import com.plotfail.plotfailbe.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoriaRepo extends JpaRepository<Storia, Long> {
    List<Storia> findAllByAutore(Utente autore);
    Optional<Storia> findById(Long id);
}
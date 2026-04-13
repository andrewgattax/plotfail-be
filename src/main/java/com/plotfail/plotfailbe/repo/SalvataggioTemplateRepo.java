package com.plotfail.plotfailbe.repo;

import com.plotfail.plotfailbe.model.SalvataggioTemplate;
import com.plotfail.plotfailbe.model.SalvataggioTemplateId;
import com.plotfail.plotfailbe.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalvataggioTemplateRepo extends JpaRepository<SalvataggioTemplate, SalvataggioTemplateId> {
    List<SalvataggioTemplate> findAllByUser(Utente user);
    Optional<SalvataggioTemplate> findByTemplateIdAndUser(Long templateId, Utente user);
}

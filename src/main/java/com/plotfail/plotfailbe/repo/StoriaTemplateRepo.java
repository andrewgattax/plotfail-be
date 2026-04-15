package com.plotfail.plotfailbe.repo;

import com.plotfail.plotfailbe.model.StoriaTemplate;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoriaTemplateRepo extends JpaRepository<StoriaTemplate, Long> {
    Optional<StoriaTemplate> findById(Long id);

    List<StoriaTemplate> findAll();

    List<StoriaTemplate> findByUsed(boolean used);
}

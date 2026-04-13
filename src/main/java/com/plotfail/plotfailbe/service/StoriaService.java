package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.dto.request.CreaStoriaRequest;
import com.plotfail.plotfailbe.dto.response.StoriaCompactResponse;
import com.plotfail.plotfailbe.dto.response.StoriaResponse;
import com.plotfail.plotfailbe.exception.RecordNotFoundException;
import com.plotfail.plotfailbe.exception.StoriaNonTuaException;
import com.plotfail.plotfailbe.model.Storia;
import com.plotfail.plotfailbe.model.StoriaTemplate;
import com.plotfail.plotfailbe.repo.StoriaRepo;
import com.plotfail.plotfailbe.repo.StoriaTemplateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoriaService {
    private final StoriaTemplateRepo storiaTemplateRepo;
    private final StoriaRepo storiaRepo;
    private final UtilitiesService utilitiesService;

    public void creaStoria(CreaStoriaRequest request) {
        Storia storia = new Storia();
        storia.setTitolo(request.getTitolo());
        storia.setContenuto(request.getContenuto());
        storia.setPubblico(request.isPubblico());
        storia.setAutore(utilitiesService.getUtente());

        StoriaTemplate storiaTemplate = storiaTemplateRepo.findById(request.getTemplateId())
                .orElseThrow(() -> new RecordNotFoundException("Template non trovato")
        );

        storia.setTemplate(storiaTemplate);

        storiaRepo.save(storia);
    }

    public void eliminaStoria(Long id) {
        Optional<Storia> storia = storiaRepo.findById(id);
        if (storia.isPresent()) {
            if (!storia.get().getAutore().equals(utilitiesService.getUtente())) {
                throw new StoriaNonTuaException("Questa storia non è tua.");
            }
            storiaRepo.deleteById(id);
        }
    }

    public void togglePubblico(Long id) {
        Storia storia = storiaRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Storia non trovata con id: " + id));
        if (!storia.getAutore().equals(utilitiesService.getUtente())) {
            throw new StoriaNonTuaException("Questa storia non è tua.");
        }
        storia.setPubblico(!storia.isPubblico());
        storiaRepo.save(storia);
    }

    public List<StoriaCompactResponse> getStorie() {
        List<Storia> storie = storiaRepo.findAllByAutore(utilitiesService.getUtente());
        return storie.stream().map((storia) -> StoriaCompactResponse.builder()
                .id(storia.getId())
                .titolo(storia.getTitolo())
                .isPublic(storia.isPubblico())
                .build()).toList();
    }

    public StoriaResponse getStoria(Long id) {
        Storia storia = storiaRepo.findById(id).orElseThrow(() -> new RecordNotFoundException("Storia non trovata con id: " + id));
        if(!storia.isPubblico() && !storia.getAutore().equals(utilitiesService.getUtente())) {
            throw new StoriaNonTuaException("Questa storia non è tua.");
        }
        return StoriaResponse.builder()
                .id(storia.getId())
                .titolo(storia.getTitolo())
                .isPubblico(storia.isPubblico())
                .templateId(storia.getTemplate().getId())
                .autore(storia.getAutore().getUsername())
                .contenuto(storia.getContenuto())
                .build();
    }
}

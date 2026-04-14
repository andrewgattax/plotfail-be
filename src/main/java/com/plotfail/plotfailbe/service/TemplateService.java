package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.dto.request.CreaTemplateRequest;
import com.plotfail.plotfailbe.dto.request.GeneraStoriaRequest;
import com.plotfail.plotfailbe.dto.response.GeneraStoriaResponse;
import com.plotfail.plotfailbe.dto.response.TemplateCompactResponse;
import com.plotfail.plotfailbe.dto.response.TemplateResponse;
import com.plotfail.plotfailbe.exception.RecordNotFoundException;
import com.plotfail.plotfailbe.model.*;
import com.plotfail.plotfailbe.repo.SalvataggioTemplateRepo;
import com.plotfail.plotfailbe.repo.StoriaTemplateRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService {

    private final StoriaTemplateRepo storiaTemplateRepo;
    private final UtilitiesService utilitiesService;
    private final SalvataggioTemplateRepo salvataggioTemplateRepo;
    private final N8NService n8NService;

    public List<TemplateCompactResponse> getTemplates() {
            return storiaTemplateRepo.findByUsed(true).stream()
                    .map(storiaTemplate -> TemplateCompactResponse.builder()
                        .id(storiaTemplate.getId())
                        .titolo(storiaTemplate.getTitolo())
                        .preview(generatePreview(storiaTemplate.getTemplate()))
                        .categoria(storiaTemplate.getCategoria())
                        .storieCreateCount(storiaTemplate.getStorieCreate().size())
                        .build())
                .toList();
    }

    public TemplateResponse getTemplate(Long id) {
        StoriaTemplate storiaTemplate = storiaTemplateRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Template non trovato con id: " + id));

        return TemplateResponse.builder()
                .titolo(storiaTemplate.getTitolo())
                .contenuto(storiaTemplate.getTemplate())
                .categoria(storiaTemplate.getCategoria().toString())
                .status(storiaTemplate.getStatoGenerazione())
                .used(storiaTemplate.isUsed())
                .storieCreateCount(storiaTemplate.getStorieCreate().size())
                .build();
    }
    
    private String generatePreview(String contenuto) {
        if (contenuto == null || contenuto.isEmpty()) {
            return "";
        }

        String[] words = contenuto.trim().split("\\s+");
        if (words.length <= 50) {
            return contenuto;
        }

        StringBuilder preview = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            preview.append(words[i]);
            if (i < 49) {
                preview.append(" ");
            }
        }
        preview.append("...");

        return preview.toString();
    }

    public void salvaTemplate(Long id){
        StoriaTemplate storiaTemplate = storiaTemplateRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Template non trovato con id: " + id));

        SalvataggioTemplate salvataggio = new SalvataggioTemplate();
        salvataggio.setTemplate(storiaTemplate);
        salvataggio.setUser(utilitiesService.getUtente());
        salvataggio.setId(new SalvataggioTemplateId(utilitiesService.getUtente().getId(), storiaTemplate.getId()));

        salvataggioTemplateRepo.save(salvataggio);
    }

    public List<TemplateCompactResponse> findSalvati() {
        List<StoriaTemplate> storiaTemplates = salvataggioTemplateRepo.findAllByUser(utilitiesService.getUtente()).stream()
                .map(SalvataggioTemplate::getTemplate)
                .filter((template) -> (template.isUsed() && template.getStatoGenerazione().equals(StatoGenerazione.COMPLETED)))
                .toList();

        return storiaTemplates.stream()
                .map(storiaTemplate -> TemplateCompactResponse.builder()
                        .id(storiaTemplate.getId())
                        .titolo(storiaTemplate.getTitolo())
                        .preview(generatePreview(storiaTemplate.getTemplate()))
                        .categoria(storiaTemplate.getCategoria())
                        .storieCreateCount(storiaTemplate.getStorieCreate().size())
                        .build())
                .toList();
    }

    public void eliminaSalvataggio(Long id) {
        SalvataggioTemplate salvataggioTemplate = salvataggioTemplateRepo.findByTemplateIdAndUser(id, utilitiesService.getUtente())
                .orElseThrow(() -> new RecordNotFoundException("Salvataggio non trovato con id: " + id));
        salvataggioTemplateRepo.delete(salvataggioTemplate);
    }

    public void creaTemplate(CreaTemplateRequest request) {
        StoriaTemplate storiaTemplate = StoriaTemplate.builder()
                .titolo(request.getTitolo())
                .statoGenerazione(StatoGenerazione.COMPLETED)
                .used(true)
                .template(request.getContenuto())
                .categoria(CategoriaTemplate.valueOf(request.getCategoria()))
                .createdAt(Instant.now())
                .build();
        storiaTemplateRepo.save(storiaTemplate);
    }

    // generastoria
    public GeneraStoriaResponse generaStoria(GeneraStoriaRequest request) {

        StoriaTemplate storiaTemplate = StoriaTemplate.builder()
                .titolo(null)
                .template(null)
                .statoGenerazione(StatoGenerazione.PROCESSING)
                .used(false)
                .createdAt(Instant.now())
                .categoria(request.getCategoria())
                .build();

        Long storiaId = storiaTemplateRepo.save(storiaTemplate).getId();

        n8NService.generaStoria(request, storiaId);

        return GeneraStoriaResponse.builder()
                .status(StatoGenerazione.PROCESSING.toString())
                .id(storiaId)
                .categoria(request.getCategoria())
                .build();
    }

    // like storia (update)
    public TemplateResponse usaTemplate(Long id) {
        StoriaTemplate storiaTemplate = storiaTemplateRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Template non trovato con id: " + id));

        storiaTemplate.setUsed(true);
        StoriaTemplate updated = storiaTemplateRepo.save(storiaTemplate);

        return TemplateResponse.builder()
                .titolo(updated.getTitolo())
                .contenuto(updated.getTemplate())
                .categoria(updated.getCategoria().toString())
                .status(updated.getStatoGenerazione())
                .used(updated.isUsed())
                .storieCreateCount(updated.getStorieCreate().size())
                .build();
    }



}

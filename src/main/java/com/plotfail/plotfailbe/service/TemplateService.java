package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.dto.request.CreaTemplateRequest;
import com.plotfail.plotfailbe.dto.response.TemplateCompactResponse;
import com.plotfail.plotfailbe.exception.RecordNotFoundException;
import com.plotfail.plotfailbe.model.CategoriaTemplate;
import com.plotfail.plotfailbe.model.SalvataggioTemplate;
import com.plotfail.plotfailbe.model.SalvataggioTemplateId;
import com.plotfail.plotfailbe.model.StoriaTemplate;
import com.plotfail.plotfailbe.repo.SalvataggioTemplateRepo;
import com.plotfail.plotfailbe.repo.StoriaTemplateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final StoriaTemplateRepo storiaTemplateRepo;
    private final UtilitiesService utilitiesService;
    private final SalvataggioTemplateRepo salvataggioTemplateRepo;

    public List<TemplateCompactResponse> getTemplates() {
            return storiaTemplateRepo.findAll().stream()
                    .map(storiaTemplate -> TemplateCompactResponse.builder()
                        .id(storiaTemplate.getId())
                        .titolo(storiaTemplate.getTitolo())
                        .preview(generatePreview(storiaTemplate.getTemplate()))
                        .categoria(storiaTemplate.getCategoria())
                        .storieCreateCount(storiaTemplate.getStorieCreate().size())
                        .build())
                .toList();
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
                .template(request.getContenuto())
                .categoria(CategoriaTemplate.valueOf(request.getCategoria()))
                .createdAt(Instant.now())
                .build();
        storiaTemplateRepo.save(storiaTemplate);
    }

}

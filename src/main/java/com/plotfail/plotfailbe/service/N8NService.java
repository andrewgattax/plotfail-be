package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.dto.request.GeneraStoriaRequest;
import com.plotfail.plotfailbe.dto.request.NotifyFinishedRequest;
import com.plotfail.plotfailbe.dto.response.GeneraStoriaResponse;
import com.plotfail.plotfailbe.dto.response.N8NStoriaResponse;
import com.plotfail.plotfailbe.exception.N8NException;
import com.plotfail.plotfailbe.exception.RecordNotFoundException;
import com.plotfail.plotfailbe.model.StatoGenerazione;
import com.plotfail.plotfailbe.model.StoriaTemplate;
import com.plotfail.plotfailbe.repo.StoriaTemplateRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class N8NService {
    private final WebClient webClient;
    private final StoriaTemplateRepo storiaTemplateRepo;

    public N8NService(StoriaTemplateRepo storiaTemplateRepo) {
        this.webClient = WebClient.builder()
                .baseUrl("https://n8n.0tb.it")
                .build();
        this.storiaTemplateRepo = storiaTemplateRepo;
    }

    public void generaStoria(GeneraStoriaRequest request, Long storiaId) {
        webClient.post()
            .uri("/webhook/6b79515f-73ba-4d09-a4bf-92b89d004dd3")
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse ->
                    Mono.error(new N8NException("Errore nella generazione della storia"))
            )
            .bodyToMono(N8NStoriaResponse.class)
            .flatMap(response -> {
                if (response.getContent() == null || response.getContent().isEmpty()) {
                    throw new N8NException("Contenuto generato da n8n è vuoto");
                }

                NotifyFinishedRequest notifyRequest = NotifyFinishedRequest.builder()
                        .idStoria(storiaId)
                        .contenuto(response.getContent())
                        .titolo(response.getTitle())
                        .build();

                completeGeneration(notifyRequest);
                return Mono.empty();
            })
            .onErrorResume(e -> {
                failGeneration(storiaId);
                log.error("Errore durante la generazione della storia con n8n: {}", e.getMessage());
                return Mono.empty();
            })
            .subscribe();
    }

    private void completeGeneration(NotifyFinishedRequest request) {
        StoriaTemplate storiaTemplate = storiaTemplateRepo.findById(request.getIdStoria())
                .orElseThrow(() -> new RecordNotFoundException("Template non trovato con id: " + request.getIdStoria()));

        storiaTemplate.setTemplate(request.getContenuto());
        storiaTemplate.setTitolo(request.getTitolo());
        storiaTemplate.setStatoGenerazione(StatoGenerazione.COMPLETED);

        storiaTemplateRepo.save(storiaTemplate);
    }

    private void failGeneration(Long storiaId) {
        StoriaTemplate storiaTemplate = storiaTemplateRepo.findById(storiaId)
                .orElseThrow(() -> new RecordNotFoundException("Template non trovato con id: " + storiaId));

        storiaTemplate.setTemplate(null);
        storiaTemplate.setTitolo(null);
        storiaTemplate.setStatoGenerazione(StatoGenerazione.FAILED);

        storiaTemplateRepo.save(storiaTemplate);
    }
}

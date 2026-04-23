package com.plotfail.plotfailbe.service;

import com.plotfail.plotfailbe.dto.response.TemplateResponse;
import com.plotfail.plotfailbe.model.StatoGenerazione;
import com.plotfail.plotfailbe.model.StoriaTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmitterService {

    private final Map<Long, SseEmitter> templateEmitters = new ConcurrentHashMap<>();

    public SseEmitter registerTemplateEmitter(Long templateId) {
        Optional<SseEmitter> existingEmitter = Optional.ofNullable(templateEmitters.get(templateId));
        existingEmitter.ifPresent(sseEmitter -> {
            sseEmitter.completeWithError(new IllegalStateException("Un nuovo client si è connesso, chiudo la connessione precedente"));
        });
        SseEmitter emitter = new SseEmitter(300_000L);
        Runnable cleanupTask = () -> {templateEmitters.remove(templateId);};
        Runnable timeoutTask = () -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("template-timeout")
                        .data(
                                "Fra addio"
                        )
                );
            } catch (IOException e) {
                log.error("Erro ao carregar template-timeout", e);
            }
            cleanupTask.run();
        };
        emitter.onCompletion(cleanupTask);
        emitter.onTimeout(timeoutTask);
        emitter.onError(e -> cleanupTask.run());
        templateEmitters.put(templateId, emitter);
        return emitter;
    }

    public void emitTemplateStatus(StoriaTemplate template) {
        Optional<SseEmitter> emitterOptional = Optional.ofNullable(templateEmitters.get(template.getId()));
         emitterOptional.ifPresentOrElse(emitter -> {
             TemplateResponse response = TemplateResponse.builder()
                     .status(template.getStatoGenerazione())
                     .id(template.getId())
                     .titolo(template.getTitolo())
                     .contenuto(template.getTemplate())
                     .categoria(template.getCategoria().toString())
                     .build();
             try {
                 emitter.send(SseEmitter.event()
                         .name("template-status")
                         .data(
                                 response, MediaType.APPLICATION_JSON
                         )
                    );
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             emitter.complete();
         }, () -> {
             log.error("Non ho trovato l'emitter con templateId {}", template.getId());
         });
    }

}

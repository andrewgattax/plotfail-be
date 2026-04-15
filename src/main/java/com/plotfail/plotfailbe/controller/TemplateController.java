package com.plotfail.plotfailbe.controller;

import com.plotfail.plotfailbe.dto.request.CreaTemplateRequest;
import com.plotfail.plotfailbe.dto.request.GeneraStoriaRequest;
import com.plotfail.plotfailbe.dto.response.GeneraStoriaResponse;
import com.plotfail.plotfailbe.dto.response.TemplateCompactResponse;
import com.plotfail.plotfailbe.dto.response.TemplateResponse;
import com.plotfail.plotfailbe.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;
    // getTemplates
    @GetMapping
    public ResponseEntity<List<TemplateCompactResponse>> getTemplates() {
        List<TemplateCompactResponse> templates = templateService.getTemplates();
        return ResponseEntity.ok(templates);
    }

    //getTemplate
    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> getTemplateById(@PathVariable Long id) {
        TemplateResponse response = templateService.getTemplate(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<TemplateCompactResponse>> getTrendingTemplates() {
        return ResponseEntity.ok(templateService.getTrendingTemplates());
    }

    // SaveTemplate
    @PostMapping("/save/{id}")
    public ResponseEntity<Void> saveTemplate(@PathVariable Long id) {
        templateService.salvaTemplate(id);
        return ResponseEntity.ok().build();
    }


    // UnsaveTemplate
    @DeleteMapping("/unsave/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        templateService.eliminaSalvataggio(id);
        return ResponseEntity.ok().build();
    }

    // Get salvati
    @GetMapping("/saved")
    public ResponseEntity<List<TemplateCompactResponse>> getSavedTemplates() {
        List<TemplateCompactResponse> savedTemplates = templateService.findSalvati();
        return ResponseEntity.ok(savedTemplates);
    }

    // generaTemplate
    @PostMapping("/genera-storia")
    public ResponseEntity<GeneraStoriaResponse> generaStoria(@Valid @RequestBody GeneraStoriaRequest request) {
        GeneraStoriaResponse storiaResponse = templateService.generaStoria(request);
        return ResponseEntity.ok(storiaResponse);
    }

    // creaTemplate
    @PostMapping
    public ResponseEntity<Void> creaTemplate(@Valid @RequestBody CreaTemplateRequest request) {
        templateService.creaTemplate(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/use-template/{id}")
    public ResponseEntity<TemplateResponse> likeTemplate(@PathVariable Long id) {
        TemplateResponse response = templateService.usaTemplate(id);
        return ResponseEntity.ok(response);
    }
}

package com.plotfail.plotfailbe.controller;

import com.plotfail.plotfailbe.dto.request.CreaStoriaRequest;
import com.plotfail.plotfailbe.dto.response.StoriaCompactResponse;
import com.plotfail.plotfailbe.dto.response.StoriaResponse;
import com.plotfail.plotfailbe.model.Storia;
import com.plotfail.plotfailbe.service.StoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storia")
@RequiredArgsConstructor
public class StoriaController {
    private final StoriaService storiaService;

    @PostMapping
    public ResponseEntity<Void> creaStoria(@Valid @RequestBody CreaStoriaRequest request) {
        storiaService.creaStoria(request);
        return ResponseEntity.ok().build();
    }

    // elimina storia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaStoria(@PathVariable Long id) {
        storiaService.eliminaStoria(id);
        return ResponseEntity.ok().build();
    }

    // toggle pubblica
    @PostMapping("/toggle-public/{id}")
    public ResponseEntity<Void> togglePublicStoria(@PathVariable Long id) {
        storiaService.togglePubblico(id);
        return ResponseEntity.ok().build();
    }

    // get storie
    @GetMapping
    public ResponseEntity<List<StoriaCompactResponse>> findAll() {
        List<StoriaCompactResponse> storie = storiaService.getStorie();
        return ResponseEntity.ok(storie);
    }

    // get storia
    @GetMapping("/{id}")
    public ResponseEntity<StoriaResponse> findById(@PathVariable Long id) {
        StoriaResponse storia = storiaService.getStoria(id);
        return ResponseEntity.ok(storia);
    }

}
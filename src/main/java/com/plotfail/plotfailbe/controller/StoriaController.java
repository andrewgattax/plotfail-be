package com.plotfail.plotfailbe.controller;

import com.plotfail.plotfailbe.dto.request.CreaStoriaRequest;
import com.plotfail.plotfailbe.dto.response.StoriaCompactResponse;
import com.plotfail.plotfailbe.dto.response.StoriaResponse;
import com.plotfail.plotfailbe.exception.ErrorResponse;
import com.plotfail.plotfailbe.model.Storia;
import com.plotfail.plotfailbe.service.StoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storia")
@RequiredArgsConstructor
@Tag(name = "Storia", description = "Story management")
public class StoriaController {
    private final StoriaService storiaService;

    @PostMapping
    @Operation(summary = "Save a story")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Story created"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> salvaStoria(@Valid @RequestBody CreaStoriaRequest request) {
        storiaService.creaStoria(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete story")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Story deleted"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Story not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> eliminaStoria(@PathVariable Long id) {
        storiaService.eliminaStoria(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toggle-public/{id}")
    @Operation(summary = "Toggle public status")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Public status toggled"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Story not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> togglePublicStoria(@PathVariable Long id) {
        storiaService.togglePubblico(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get all stories of user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Stories retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<StoriaCompactResponse>> findAll() {
        List<StoriaCompactResponse> storie = storiaService.getStorie();
        return ResponseEntity.ok(storie);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get story by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Story retrieved"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Story not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<StoriaResponse> findById(@PathVariable Long id) {
        StoriaResponse storia = storiaService.getStoria(id);
        return ResponseEntity.ok(storia);
    }

}
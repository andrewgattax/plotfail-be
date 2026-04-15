package com.plotfail.plotfailbe.controller;

import com.plotfail.plotfailbe.dto.request.CreaTemplateRequest;
import com.plotfail.plotfailbe.dto.request.GeneraStoriaRequest;
import com.plotfail.plotfailbe.dto.response.GeneraStoriaResponse;
import com.plotfail.plotfailbe.dto.response.TemplateCompactResponse;
import com.plotfail.plotfailbe.dto.response.TemplateResponse;
import com.plotfail.plotfailbe.exception.ErrorResponse;
import com.plotfail.plotfailbe.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
@Tag(name = "Template", description = "Template management")
public class TemplateController {
    private final TemplateService templateService;
    @GetMapping
    @Operation(summary = "Get all templates")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Templates retrieved"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TemplateCompactResponse>> getTemplates() {
        List<TemplateCompactResponse> templates = templateService.getTemplates();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get template by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Template retrieved"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Template not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TemplateResponse> getTemplateById(@PathVariable Long id) {
        TemplateResponse response = templateService.getTemplate(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trending")
    @Operation(summary = "Get trending templates")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Trending templates retrieved"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TemplateCompactResponse>> getTrendingTemplates() {
        return ResponseEntity.ok(templateService.getTrendingTemplates());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save/{id}")
    @Operation(summary = "Save template")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Template saved"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Template not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> saveTemplate(@PathVariable Long id) {
        templateService.salvaTemplate(id);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/unsave/{id}")
    @Operation(summary = "Unsave template")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Template unsaved"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Template not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        templateService.eliminaSalvataggio(id);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/saved")
    @Operation(summary = "Get saved templates")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saved templates retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TemplateCompactResponse>> getSavedTemplates() {
        List<TemplateCompactResponse> savedTemplates = templateService.findSalvati();
        return ResponseEntity.ok(savedTemplates);
    }

    @PostMapping("/genera-storia")
    @Operation(summary = "Generate story from template")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Story generated"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GeneraStoriaResponse> generaStoria(@Valid @RequestBody GeneraStoriaRequest request) {
        GeneraStoriaResponse storiaResponse = templateService.generaStoria(request);
        return ResponseEntity.ok(storiaResponse);
    }


//    @PostMapping
//    @Operation(summary = "Create template")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", description = "Template created"),
//        @ApiResponse(responseCode = "400", description = "Bad request"),
//        @ApiResponse(responseCode = "401", description = "Unauthorized"),
//        @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//    public ResponseEntity<Void> creaTemplate(@Valid @RequestBody CreaTemplateRequest request) {
//        templateService.creaTemplate(request);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/use-template/{id}")
    @Operation(summary = "Use template")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Template used"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Template not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TemplateResponse> likeTemplate(@PathVariable Long id) {
        TemplateResponse response = templateService.usaTemplate(id);
        return ResponseEntity.ok(response);
    }
}

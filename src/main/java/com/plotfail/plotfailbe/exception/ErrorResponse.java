package com.plotfail.plotfailbe.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * DTO di risposta di errore standard per gli errori API.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "API error response")
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Error timestamp", example = "2024-01-01 12:00:00")
    private LocalDateTime timestamp;
    @Schema(description = "HTTP status code", example = "404")
    private int status;
    @Schema(description = "Error type", example = "Not Found")
    private String error;
    @Schema(description = "Error message", example = "Resource not found")
    private String message;
    @Schema(description = "Request path", example = "/api/resource/1")
    private String path;

    /**
     * Costruttore predefinito.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Costruisce una nuova risposta di errore con i parametri specificati.
     *
     * @param status il codice di stato HTTP
     * @param error il tipo di errore
     * @param message il messaggio di errore
     * @param path il percorso della richiesta
     */
    public ErrorResponse(HttpStatus status, String error, String message, String path) {
        this();
        this.status = status.value();
        this.error = error;
        this.message = message;
        this.path = path;
    }
}

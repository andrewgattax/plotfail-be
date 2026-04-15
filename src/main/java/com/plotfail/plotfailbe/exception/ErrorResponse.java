package com.plotfail.plotfailbe.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * DTO di risposta di errore standard per gli errori API.
 */
@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
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

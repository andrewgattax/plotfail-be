package com.plotfail.plotfailbe.exception;

import org.springframework.http.HttpStatus;

public class UsernameGiaEsistenteException extends BaseException{
    public UsernameGiaEsistenteException(String message) {
        super(
                message,
                HttpStatus.CONFLICT
        );
    }
}

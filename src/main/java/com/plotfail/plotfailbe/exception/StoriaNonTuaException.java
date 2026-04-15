package com.plotfail.plotfailbe.exception;

import org.springframework.http.HttpStatus;

public class StoriaNonTuaException extends BaseException {
    public StoriaNonTuaException(String message) {
        super(
                message,
                HttpStatus.FORBIDDEN
        );
    }
}

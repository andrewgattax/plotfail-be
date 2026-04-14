package com.plotfail.plotfailbe.exception;

import org.springframework.http.HttpStatus;

public class N8NException extends BaseException {
    public N8NException(String message) {
        super(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

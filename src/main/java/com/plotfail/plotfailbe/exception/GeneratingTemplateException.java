package com.plotfail.plotfailbe.exception;

import org.springframework.http.HttpStatus;

public class GeneratingTemplateException extends BaseException {
    public GeneratingTemplateException(String message) {
        super(
                message,
                HttpStatus.FORBIDDEN
        );
    }
}

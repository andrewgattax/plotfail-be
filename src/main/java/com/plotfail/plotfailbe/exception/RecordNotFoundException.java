package com.plotfail.plotfailbe.exception;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends BaseException{
    public RecordNotFoundException(String message) {
        super(
                message,
                HttpStatus.BAD_REQUEST
        );
    }
}

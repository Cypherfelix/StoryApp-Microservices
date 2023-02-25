package com.story.app.Exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private HttpStatus status;
    public BadRequestException(String message, HttpStatus unauthorized) {
        super(message);
        this.status = unauthorized;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

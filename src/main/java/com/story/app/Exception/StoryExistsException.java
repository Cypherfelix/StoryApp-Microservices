package com.story.app.Exception;

import org.springframework.http.HttpStatus;

public class StoryExistsException extends RuntimeException {
    private HttpStatus status;

    public StoryExistsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

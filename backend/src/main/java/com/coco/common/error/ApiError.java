package com.coco.common.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

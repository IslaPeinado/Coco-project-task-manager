package com.coco.common.error;

import lombok.Getter;

import java.time.Instant;
import java.util.List;


@Getter
public class ApiError {

    private final String code;
    private final String message;
    private final String path;
    private final Instant timestamp;
    private final List<Detail> details;

    public ApiError(String code, String message, String path, Instant timestamp) {
        this(code, message, path, timestamp, List.of());
    }

    public ApiError(String code, String message, String path, Instant timestamp, List<Detail> details) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
        this.details = List.copyOf(details);
    }
    
    public record Detail(String field, String message) {
    }
}

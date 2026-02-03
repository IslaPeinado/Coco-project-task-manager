package com.coco.common.error;

import com.coco.common.util.ForbiddenException;
import com.coco.common.util.ConflictException;
import com.coco.common.util.NotFoundException;
import com.coco.common.util.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> notFound(NotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ErrorCodes.NOT_FOUND, messageOrDefault(ex.getMessage(), "Resource not found"), req);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> conflict(ConflictException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ErrorCodes.CONFLICT, messageOrDefault(ex.getMessage(), "Resource conflict"), req);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        if (looksLikeNotFound(ex.getMessage())) {
            return build(HttpStatus.NOT_FOUND, ErrorCodes.NOT_FOUND, ex.getMessage(), req);
        }
        return build(HttpStatus.BAD_REQUEST, ErrorCodes.BAD_REQUEST, messageOrDefault(ex.getMessage(), "Invalid request"), req);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> forbidden(ForbiddenException ex, HttpServletRequest req) {
        return build(HttpStatus.FORBIDDEN, ErrorCodes.FORBIDDEN, messageOrDefault(ex.getMessage(), "Access denied"), req);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> unauthorized(UnauthorizedException ex, HttpServletRequest req) {
        return build(HttpStatus.UNAUTHORIZED, ErrorCodes.UNAUTHORIZED, messageOrDefault(ex.getMessage(), "Authentication required"), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiError.Detail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiError.Detail(fieldError.getField(), messageOrDefault(fieldError.getDefaultMessage(), "Invalid value")))
                .toList();

        return build(HttpStatus.BAD_REQUEST, ErrorCodes.VALIDATION_ERROR, "Invalid request", req, details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> constraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        List<ApiError.Detail> details = ex.getConstraintViolations().stream()
                .map(v -> new ApiError.Detail(v.getPropertyPath().toString(), v.getMessage()))
                .toList();

        return build(HttpStatus.BAD_REQUEST, ErrorCodes.VALIDATION_ERROR, "Invalid request", req, details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
        log.error("Unexpected error processing {} {}", req.getMethod(), req.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.INTERNAL_ERROR, "Unexpected error", req);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String code, String message, HttpServletRequest req) {
        return build(status, code, message, req, List.of());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String code, String message, HttpServletRequest req, List<ApiError.Detail> details) {
        return ResponseEntity.status(status)
                .body(new ApiError(code, message, req.getRequestURI(), Instant.now(), details));
    }

    private String messageOrDefault(String message, String defaultMessage) {
        return (message == null || message.isBlank()) ? defaultMessage : message;
    }

    private boolean looksLikeNotFound(String message) {
        return message != null && message.toLowerCase().contains("not found");
    }
}


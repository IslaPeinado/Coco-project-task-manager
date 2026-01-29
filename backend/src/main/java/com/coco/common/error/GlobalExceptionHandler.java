package com.coco.common.error;

import com.coco.common.util.ForbiddenException;
import com.coco.common.util.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbiddenException(ForbiddenException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}

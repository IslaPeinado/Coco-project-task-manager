package com.coco.security;

import com.coco.common.error.ApiError;
import com.coco.common.error.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiError error = new ApiError(
                ErrorCodes.FORBIDDEN,
                "Access denied",
                request.getRequestURI(),
                Instant.now(),
                List.of()
        );
        response.getWriter().write(toJson(error));
    }

    private String toJson(ApiError error) {
        return "{\"code\":\"" + error.getCode() + "\","
                + "\"message\":\"" + error.getMessage() + "\","
                + "\"path\":\"" + error.getPath() + "\","
                + "\"timestamp\":\"" + error.getTimestamp() + "\","
                + "\"details\":[]}";
    }
}

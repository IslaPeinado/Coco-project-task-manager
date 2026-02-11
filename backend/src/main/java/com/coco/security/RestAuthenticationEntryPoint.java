package com.coco.security;

import com.coco.common.error.ApiError;
import com.coco.common.error.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiError error = new ApiError(
                ErrorCodes.UNAUTHORIZED,
                "Authentication required",
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

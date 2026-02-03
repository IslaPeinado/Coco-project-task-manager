package com.coco.security.user;

import com.coco.common.util.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserService {

    public Optional<String> getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();
        Object principal = auth.getPrincipal();
        if (principal == null) return Optional.empty();
        return Optional.of(String.valueOf(principal));
    }

    public Long getRequiredUserId() {
        String username = getUsername()
                .filter(value -> !"anonymousUser".equals(value))
                .orElseThrow(() -> new UnauthorizedException("Authentication required"));

        try {
            return Long.valueOf(username);
        } catch (NumberFormatException ex) {
            throw new UnauthorizedException("Invalid authenticated user id");
        }
    }
}

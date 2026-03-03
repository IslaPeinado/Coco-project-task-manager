package com.coco.modules.user.application.auth;

import com.coco.modules.user.api.dto.AuthResponse;
import com.coco.modules.user.api.dto.RegisterRequest;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        OffsetDateTime now = OffsetDateTime.now();
        user.setEmail(request.email());
        user.setLogin(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.name());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        User saved = userRepository.save(user);
        if (saved.getId() == null) {
            throw new IllegalStateException("User id not generated");
        }

        String token = jwtService.generateToken(String.valueOf(saved.getId()));

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}

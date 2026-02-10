package com.coco.modules.user.application.auth;

import com.coco.modules.user.api.dto.AuthResponse;
import com.coco.modules.user.api.dto.RegisterRequest;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                user.setEmail(request.email());
                user.setPassword(passwordEncoder.encode(request.password()));
                user.setFirstName(request.name());


        userRepository.save(user);

        String token = jwtService.generateToken(String.valueOf(user));

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}

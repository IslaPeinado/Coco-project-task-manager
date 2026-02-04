package com.coco.modules.user.application.auth;

import com.coco.modules.user.api.dto.AuthResponse;
import com.coco.modules.user.api.dto.LoginRequest;
import com.coco.modules.user.application.port.PasswordEncoderPort;
import com.coco.modules.user.application.port.TokenServicePort;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.common.util.UnauthorizedException;
import com.coco.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenServicePort tokenService;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (user.getId() == null) {
            throw new UnauthorizedException("Invalid user identity");
        }

        String token = tokenService.generateAccessToken(String.valueOf(user.getId()));

        return new AuthResponse(
                token,
                "Bearer",
                tokenService.getAccessTokenExpirationSeconds()
        );
    }
}

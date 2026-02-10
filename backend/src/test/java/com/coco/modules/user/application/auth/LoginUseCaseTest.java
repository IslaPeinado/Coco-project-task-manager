package com.coco.modules.user.application.auth;

import com.coco.modules.user.api.dto.LoginRequest;
import com.coco.modules.user.application.port.PasswordEncoderPort;
import com.coco.modules.user.application.port.TokenServicePort;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.common.util.UnauthorizedException;
import com.coco.modules.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    UserRepositoryPort userRepository;
    @Mock PasswordEncoderPort passwordEncoder;
    @Mock TokenServicePort tokenService;

    @InjectMocks LoginUseCase loginUseCase;

    @Test
    void login_ok_returns_token() {
        var req = new LoginRequest("a@a.com", "pass");
        var user = new User();
        user.setId(1L);
        user.setEmail("a@a.com");
        user.setPassword("HASH");

        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "HASH")).thenReturn(true);
        when(tokenService.generateAccessToken("1")).thenReturn("TOKEN");
        when(tokenService.getAccessTokenExpirationSeconds()).thenReturn(3600L);

        var res = loginUseCase.login(req);

        assertEquals("TOKEN", res.accessToken());
        assertEquals("Bearer", res.tokenType());
        assertEquals(3600, res.expiresIn());

        verify(userRepository).findByEmail("a@a.com");
        verify(passwordEncoder).matches("pass", "HASH");
        verify(tokenService).generateAccessToken("1");
        verify(tokenService).getAccessTokenExpirationSeconds();
    }

    @Test
    void login_invalid_password_throws() {
        var req = new LoginRequest("a@a.com", "wrong");
        var user = new User();
        user.setId(1L);
        user.setEmail("a@a.com");
        user.setPassword("HASH");

        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "HASH")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> loginUseCase.login(req));
        verify(tokenService, never()).generateAccessToken(anyString());
    }

    @Test
    void login_user_not_found_throws() {
        when(userRepository.findByEmail("x@x.com")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> loginUseCase.login(new LoginRequest("x@x.com", "pass")));
        verify(tokenService, never()).generateAccessToken(anyString());
    }

    @Test
    void login_user_without_id_throws() {
        var req = new LoginRequest("a@a.com", "pass");
        var user = new User();
        user.setEmail("a@a.com");
        user.setPassword("HASH");

        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "HASH")).thenReturn(true);

        assertThrows(UnauthorizedException.class, () -> loginUseCase.login(req));
        verify(tokenService, never()).generateAccessToken(anyString());
    }
}

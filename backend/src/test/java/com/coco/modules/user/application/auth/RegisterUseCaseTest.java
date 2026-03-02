package com.coco.modules.user.application.auth;

import com.coco.modules.user.api.dto.RegisterRequest;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUseCaseTest {

    @Mock
    UserRepositoryPort userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;

    @InjectMocks RegisterUseCase registerUseCase;

    @Test
    void register_ok_saves_user_and_returns_token() {
        var req = new RegisterRequest("a@a.com", "pass", "Ana");

        when(userRepository.existsByEmail("a@a.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });
        when(passwordEncoder.encode("pass")).thenReturn("HASH");
        when(jwtService.generateToken("1")).thenReturn("TOKEN");
        when(jwtService.getExpirationSeconds()).thenReturn(3600L);

        var res = registerUseCase.register(req);

        var userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        var saved = userCaptor.getValue();
        assertEquals("a@a.com", saved.getEmail());
        assertEquals("a@a.com", saved.getLogin());
        assertEquals("HASH", saved.getPassword());
        assertEquals("Ana", saved.getFirstName());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());

        assertEquals("TOKEN", res.accessToken());
        assertEquals("Bearer", res.tokenType());
        assertEquals(3600, res.expiresIn());
        verify(jwtService).generateToken("1");
        verify(jwtService).getExpirationSeconds();
    }

    @Test
    void register_existing_email_throws() {
        when(userRepository.existsByEmail("a@a.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> registerUseCase.register(new RegisterRequest("a@a.com", "pass", "Ana")));

        verify(userRepository, never()).save(any());
        verify(jwtService, never()).generateToken(anyString());
    }
}

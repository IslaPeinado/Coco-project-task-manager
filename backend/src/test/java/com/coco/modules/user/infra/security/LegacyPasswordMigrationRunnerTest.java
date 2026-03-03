package com.coco.modules.user.infra.security;

import com.coco.modules.user.application.port.PasswordEncoderPort;
import com.coco.modules.user.infra.persistence.UserEntity;
import com.coco.modules.user.infra.persistence.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LegacyPasswordMigrationRunnerTest {

    @Mock
    UserJpaRepository userJpaRepository;
    @Mock
    PasswordEncoderPort passwordEncoder;

    @InjectMocks
    LegacyPasswordMigrationRunner runner;

    @Test
    void run_migrates_only_plaintext_passwords() throws Exception {
        UserEntity plaintextUser = new UserEntity();
        plaintextUser.setId(1L);
        plaintextUser.setPassword("plain-pass");
        plaintextUser.setUpdatedAt(OffsetDateTime.parse("2026-03-02T10:15:30Z"));

        UserEntity bcryptUser = new UserEntity();
        bcryptUser.setId(2L);
        bcryptUser.setPassword("$2a$10$abcdefghijklmnopqrstuuK1x4zP9sT9Yx0Y7YvK4L3sN3tQ5mK6");
        bcryptUser.setUpdatedAt(OffsetDateTime.parse("2026-03-02T10:15:30Z"));

        when(userJpaRepository.findAll()).thenReturn(List.of(plaintextUser, bcryptUser));
        when(passwordEncoder.encode("plain-pass")).thenReturn("HASHED_PASS");

        runner.run(new DefaultApplicationArguments(new String[0]));

        ArgumentCaptor<List<UserEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(userJpaRepository).saveAll(captor.capture());

        List<UserEntity> savedUsers = captor.getValue();
        assertEquals("HASHED_PASS", savedUsers.getFirst().getPassword());
        assertTrue(savedUsers.getFirst().getUpdatedAt().isAfter(OffsetDateTime.parse("2026-03-02T10:15:30Z")));
        assertEquals("$2a$10$abcdefghijklmnopqrstuuK1x4zP9sT9Yx0Y7YvK4L3sN3tQ5mK6", savedUsers.get(1).getPassword());
    }

    @Test
    void run_whenAllPasswordsAreAlreadyBcrypt_doesNotSave() throws Exception {
        UserEntity bcryptUser = new UserEntity();
        bcryptUser.setId(2L);
        bcryptUser.setPassword("$2b$10$abcdefghijklmnopqrstuuK1x4zP9sT9Yx0Y7YvK4L3sN3tQ5mK6");

        when(userJpaRepository.findAll()).thenReturn(List.of(bcryptUser));

        runner.run(new DefaultApplicationArguments(new String[0]));

        verify(userJpaRepository, never()).saveAll(org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void isLegacyPassword_detectsPlaintextAndBcrypt() {
        assertTrue(LegacyPasswordMigrationRunner.isLegacyPassword("plain-pass"));
        assertFalse(LegacyPasswordMigrationRunner.isLegacyPassword("$2a$10$abcdefghijklmnopqrstuuK1x4zP9sT9Yx0Y7YvK4L3sN3tQ5mK6"));
        assertFalse(LegacyPasswordMigrationRunner.isLegacyPassword("$2b$10$abcdefghijklmnopqrstuuK1x4zP9sT9Yx0Y7YvK4L3sN3tQ5mK6"));
        assertFalse(LegacyPasswordMigrationRunner.isLegacyPassword("$2y$10$abcdefghijklmnopqrstuuK1x4zP9sT9Yx0Y7YvK4L3sN3tQ5mK6"));
    }
}

package com.coco.modules.user.application.user;

import com.coco.common.util.ConflictException;
import com.coco.modules.user.api.dto.UserUpdateRequest;
import com.coco.modules.user.api.mapper.UserApiMapper;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.user.CurrentUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    UserRepositoryPort userRepository;
    @Mock
    CurrentUserService currentUserService;

    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        updateUserUseCase = new UpdateUserUseCase(userRepository, currentUserService, new UserApiMapper());
    }

    @Test
    void execute_updates_current_user_profile() {
        OffsetDateTime createdAt = OffsetDateTime.parse("2026-03-02T10:15:30Z");
        User user = new User();
        user.setId(7L);
        user.setEmail("old@coco.dev");
        user.setLogin("old@coco.dev");
        user.setPassword("HASH");
        user.setFirstName("Old");
        user.setLastName("Name");
        user.setCreatedAt(createdAt);

        UserUpdateRequest request = new UserUpdateRequest("new@coco.dev", "New", "User", "https://img.dev/a.png");

        when(currentUserService.getRequiredUserId()).thenReturn(7L);
        when(userRepository.findById(7L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@coco.dev")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = updateUserUseCase.execute(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User persisted = captor.getValue();
        assertEquals("new@coco.dev", persisted.getEmail());
        assertEquals("new@coco.dev", persisted.getLogin());
        assertEquals("New", persisted.getFirstName());
        assertEquals("User", persisted.getLastName());
        assertEquals("https://img.dev/a.png", persisted.getImageUrl());
        assertEquals("HASH", persisted.getPassword());
        assertEquals(createdAt, persisted.getCreatedAt());
        assertNotNull(persisted.getUpdatedAt());

        assertEquals("new@coco.dev", updated.getEmail());
    }

    @Test
    void execute_whenEmailAlreadyExists_throwsConflict() {
        User user = new User();
        user.setId(7L);
        user.setEmail("old@coco.dev");

        when(currentUserService.getRequiredUserId()).thenReturn(7L);
        when(userRepository.findById(7L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("used@coco.dev")).thenReturn(true);

        assertThrows(ConflictException.class, () ->
                updateUserUseCase.execute(new UserUpdateRequest("used@coco.dev", "Ana", "User", null)));

        verify(userRepository, never()).save(any());
    }
}

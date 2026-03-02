package com.coco.modules.user.application.user;

import com.coco.common.util.UnauthorizedException;
import com.coco.modules.user.api.dto.ChangePasswordRequest;
import com.coco.modules.user.application.port.PasswordEncoderPort;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.user.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangePasswordUseCaseTest {

    @Mock
    UserRepositoryPort userRepository;
    @Mock
    PasswordEncoderPort passwordEncoder;
    @Mock
    CurrentUserService currentUserService;

    @InjectMocks
    ChangePasswordUseCase changePasswordUseCase;

    @Test
    void execute_updates_password_whenCurrentPasswordMatches() {
        User user = new User();
        user.setId(5L);
        user.setPassword("OLD_HASH");

        when(currentUserService.getRequiredUserId()).thenReturn(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old-pass", "OLD_HASH")).thenReturn(true);
        when(passwordEncoder.encode("new-pass")).thenReturn("NEW_HASH");

        changePasswordUseCase.execute(new ChangePasswordRequest("old-pass", "new-pass"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User persisted = captor.getValue();
        assertEquals("NEW_HASH", persisted.getPassword());
        assertNotNull(persisted.getUpdatedAt());
    }

    @Test
    void execute_whenCurrentPasswordDoesNotMatch_throwsUnauthorized() {
        User user = new User();
        user.setId(5L);
        user.setPassword("OLD_HASH");

        when(currentUserService.getRequiredUserId()).thenReturn(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-pass", "OLD_HASH")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () ->
                changePasswordUseCase.execute(new ChangePasswordRequest("wrong-pass", "new-pass")));

        verify(userRepository, never()).save(any());
    }

    @Test
    void execute_whenNewPasswordMatchesCurrent_throwsBadRequest() {
        User user = new User();
        user.setId(5L);
        user.setPassword("OLD_HASH");

        when(currentUserService.getRequiredUserId()).thenReturn(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("same-pass", "OLD_HASH")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                changePasswordUseCase.execute(new ChangePasswordRequest("same-pass", "same-pass")));

        verify(userRepository, never()).save(any());
    }
}

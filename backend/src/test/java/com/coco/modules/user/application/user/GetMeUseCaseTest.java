package com.coco.modules.user.application.user;

import com.coco.common.util.NotFoundException;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.user.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMeUseCaseTest {

    @Mock
    private UserRepositoryPort userRepository;
    @Mock
    private CurrentUserService currentUserService;

    @InjectMocks
    private GetMeUseCase getMeUseCase;

    @Test
    void execute_returnsCurrentAuthenticatedUser() {
        User user = new User();
        user.setId(5L);
        user.setEmail("ana@coco.dev");

        when(currentUserService.getRequiredUserId()).thenReturn(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));

        User result = getMeUseCase.execute();

        assertEquals(5L, result.getId());
        assertEquals("ana@coco.dev", result.getEmail());
    }

    @Test
    void execute_whenCurrentUserDoesNotExist_throwsNotFound() {
        when(currentUserService.getRequiredUserId()).thenReturn(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> getMeUseCase.execute());
    }
}

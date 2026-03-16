package com.coco.modules.user.application.user;

import com.coco.common.util.NotFoundException;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
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
class GetUserUseCaseTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private GetUserUseCase getUserUseCase;

    @Test
    void execute_returnsUserById() {
        User user = new User();
        user.setId(9L);
        user.setEmail("team@coco.dev");

        when(userRepository.findById(9L)).thenReturn(Optional.of(user));

        User result = getUserUseCase.execute(9L);

        assertEquals(9L, result.getId());
        assertEquals("team@coco.dev", result.getEmail());
    }

    @Test
    void execute_whenUserDoesNotExist_throwsNotFound() {
        when(userRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> getUserUseCase.execute(9L));
    }
}

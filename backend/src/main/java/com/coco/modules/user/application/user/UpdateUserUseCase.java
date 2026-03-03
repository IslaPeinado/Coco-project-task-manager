package com.coco.modules.user.application.user;

import com.coco.common.util.ConflictException;
import com.coco.common.util.NotFoundException;
import com.coco.modules.user.api.dto.UserUpdateRequest;
import com.coco.modules.user.api.mapper.UserApiMapper;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final CurrentUserService currentUserService;
    private final UserApiMapper userApiMapper;

    public User execute(UserUpdateRequest request) {
        Long userId = currentUserService.getRequiredUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean emailChanged = !user.getEmail().equalsIgnoreCase(request.email());
        if (emailChanged && userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already in use");
        }

        userApiMapper.applyUpdate(request, user);
        user.setUpdatedAt(OffsetDateTime.now());

        return userRepository.save(user);
    }
}

package com.coco.modules.user.application.user;

import com.coco.common.util.NotFoundException;
import com.coco.common.util.UnauthorizedException;
import com.coco.modules.user.api.dto.ChangePasswordRequest;
import com.coco.modules.user.application.port.PasswordEncoderPort;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final CurrentUserService currentUserService;

    public void execute(ChangePasswordRequest request) {
        Long userId = currentUserService.getRequiredUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }

        if (request.currentPassword().equals(request.newPassword())) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setUpdatedAt(OffsetDateTime.now());
        userRepository.save(user);
    }
}

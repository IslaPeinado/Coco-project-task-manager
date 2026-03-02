package com.coco.modules.user.application.user;

import com.coco.common.util.NotFoundException;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import com.coco.security.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMeUseCase {

    private final UserRepositoryPort userRepository;
    private final CurrentUserService currentUserService;

    public User execute() {
        Long userId = currentUserService.getRequiredUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

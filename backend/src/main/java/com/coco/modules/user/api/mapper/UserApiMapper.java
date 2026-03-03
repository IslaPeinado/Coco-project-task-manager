package com.coco.modules.user.api.mapper;

import com.coco.modules.user.api.dto.UserResponse;
import com.coco.modules.user.api.dto.UserUpdateRequest;
import com.coco.modules.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserApiMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getImageUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public void applyUpdate(UserUpdateRequest request, User user) {
        user.setEmail(request.email());
        user.setLogin(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setImageUrl(request.imageUrl());
    }
}

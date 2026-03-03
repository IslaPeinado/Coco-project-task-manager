package com.coco.modules.user.infra.mapper;

import com.coco.modules.user.domain.User;
import com.coco.modules.user.infra.persistence.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User user) {
        return UserEntity.fromDomain(user);
    }

    public User toDomain(UserEntity entity) {
        return entity.toDomain();
    }
}

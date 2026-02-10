package com.coco.modules.user.infra.persistence;



import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        if (user.getId() != null) {
            entity.setId(user.getId());
        }
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setImageUrl(user.getImageUrl());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        UserEntity saved = userJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toDomain);    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }


    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);

    }
}
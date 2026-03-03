package com.coco.modules.user.infra.security;

import com.coco.modules.user.application.port.PasswordEncoderPort;
import com.coco.modules.user.infra.persistence.UserEntity;
import com.coco.modules.user.infra.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegacyPasswordMigrationRunner implements ApplicationRunner {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoderPort passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<UserEntity> users = userJpaRepository.findAll();
        int migrated = 0;

        for (UserEntity user : users) {
            if (isLegacyPassword(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUpdatedAt(OffsetDateTime.now());
                migrated++;
            }
        }

        if (migrated > 0) {
            userJpaRepository.saveAll(users);
            log.info("Migrated {} legacy user passwords to BCrypt", migrated);
            return;
        }

        log.debug("No legacy user passwords found");
    }

    static boolean isLegacyPassword(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }

        return !(password.startsWith("$2a$")
                || password.startsWith("$2b$")
                || password.startsWith("$2y$"));
    }
}

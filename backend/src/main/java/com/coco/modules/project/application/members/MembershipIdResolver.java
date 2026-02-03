package com.coco.modules.project.application.members;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MembershipIdResolver {

    private final JdbcTemplate jdbcTemplate;

    public MembershipIdResolver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long ownerId() {
        Long ownerId = findRoleId("OWNER");
        if (ownerId != null) {
            return ownerId;
        }

        // Backward compatibility with existing seeds that use ADMIN.
        Long adminId = findRoleId("ADMIN");
        if (adminId != null) {
            return adminId;
        }

        throw new IllegalStateException("Owner role not found (OWNER/ADMIN)");
    }

    private Long findRoleId(String roleName) {
        var results = jdbcTemplate.query(
                "SELECT id FROM role WHERE role_name = ? LIMIT 1",
                (rs, rowNum) -> rs.getLong("id"),
                roleName
        );
        return results.isEmpty() ? null : results.get(0);
    }
}

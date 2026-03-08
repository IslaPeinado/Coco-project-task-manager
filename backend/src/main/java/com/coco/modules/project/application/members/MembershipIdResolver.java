package com.coco.modules.project.application.members;

import com.coco.modules.project.domain.ProjectRole;
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
        throw new IllegalStateException("Owner role not found (OWNER)");
    }

    public Long managerId() {
        Long managerId = findRoleId("MANAGER");
        if (managerId != null) {
            return managerId;
        }
        throw new IllegalStateException("Manager role not found (MANAGER)");
    }

    public Long memberId() {
        Long memberId = findRoleId("MEMBER");
        if (memberId != null) {
            return memberId;
        }
        throw new IllegalStateException("Member role not found (MEMBER)");
    }

    public Long viewerId() {
        Long viewerId = findRoleId("VIEWER");
        if (viewerId != null) {
            return viewerId;
        }
        throw new IllegalStateException("Viewer role not found (VIEWER)");
    }

    public ProjectRole toProjectRole(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role id is required");
        }
        if (roleId.equals(ownerId())) return ProjectRole.OWNER;
        if (roleId.equals(managerId())) return ProjectRole.MANAGER;
        if (roleId.equals(memberId())) return ProjectRole.MEMBER;
        if (roleId.equals(viewerId())) return ProjectRole.VIEWER;
        throw new IllegalArgumentException("Unknown project role id: " + roleId);
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

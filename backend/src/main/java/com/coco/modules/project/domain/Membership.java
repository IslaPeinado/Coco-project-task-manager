package com.coco.modules.project.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


/**
 * Representa la pertenencia de un usuario a un proyecto con un rol (roleId).
 * Se apoya en la tabla cocouser_project_role: (user_id, project_id) PK compuesta + role_id.
 */
@Getter
@Setter
public class Membership {


    private Long userId;
    private Long projectId;
    private Long roleId;

    public Membership() {}

    public Membership(Long userId, Long projectId, Long roleId) {
        this.userId = userId;
        this.projectId = projectId;
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membership that)) return false;
        return Objects.equals(userId, that.userId)
                && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, projectId);
    }
}

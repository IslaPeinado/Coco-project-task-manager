package com.coco.modules.project.infra.security;

import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.project.domain.ProjectRole;

public final class ProjectPolicy {

    private ProjectPolicy() {
    }

    public static boolean allows(ProjectRole role, ProjectPermission permission) {
        return switch (permission) {
            case READ -> true;
            case WRITE -> role == ProjectRole.OWNER
                    || role == ProjectRole.MANAGER
                    || role == ProjectRole.MEMBER;
            case MANAGE -> role == ProjectRole.OWNER
                    || role == ProjectRole.MANAGER;
            case OWNER_ONLY -> role == ProjectRole.OWNER;
        };
    }
}

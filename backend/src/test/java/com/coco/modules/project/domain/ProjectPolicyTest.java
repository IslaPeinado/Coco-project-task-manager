package com.coco.modules.project.domain;

import com.coco.modules.project.infra.security.ProjectPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectPolicyTest {

    @Test
    void read_is_allowed_for_all_roles() {
        assertTrue(ProjectPolicy.allows(ProjectRole.OWNER, ProjectPermission.READ));
        assertTrue(ProjectPolicy.allows(ProjectRole.MANAGER, ProjectPermission.READ));
        assertTrue(ProjectPolicy.allows(ProjectRole.MEMBER, ProjectPermission.READ));
        assertTrue(ProjectPolicy.allows(ProjectRole.VIEWER, ProjectPermission.READ));
    }

    @Test
    void write_is_denied_for_viewer_only() {
        assertTrue(ProjectPolicy.allows(ProjectRole.OWNER, ProjectPermission.WRITE));
        assertTrue(ProjectPolicy.allows(ProjectRole.MANAGER, ProjectPermission.WRITE));
        assertTrue(ProjectPolicy.allows(ProjectRole.MEMBER, ProjectPermission.WRITE));
        assertFalse(ProjectPolicy.allows(ProjectRole.VIEWER, ProjectPermission.WRITE));
    }

    @Test
    void manage_is_allowed_only_for_owner_and_manager() {
        assertTrue(ProjectPolicy.allows(ProjectRole.OWNER, ProjectPermission.MANAGE));
        assertTrue(ProjectPolicy.allows(ProjectRole.MANAGER, ProjectPermission.MANAGE));
        assertFalse(ProjectPolicy.allows(ProjectRole.MEMBER, ProjectPermission.MANAGE));
        assertFalse(ProjectPolicy.allows(ProjectRole.VIEWER, ProjectPermission.MANAGE));
    }

    @Test
    void owner_only_is_allowed_only_for_owner() {
        assertTrue(ProjectPolicy.allows(ProjectRole.OWNER, ProjectPermission.OWNER_ONLY));
        assertFalse(ProjectPolicy.allows(ProjectRole.MANAGER, ProjectPermission.OWNER_ONLY));
        assertFalse(ProjectPolicy.allows(ProjectRole.MEMBER, ProjectPermission.OWNER_ONLY));
        assertFalse(ProjectPolicy.allows(ProjectRole.VIEWER, ProjectPermission.OWNER_ONLY));
    }
}

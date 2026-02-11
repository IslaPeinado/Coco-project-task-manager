package com.coco.modules.project.application;

import com.coco.common.util.ForbiddenException;
import com.coco.modules.project.application.members.MembershipIdResolver;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.project.infra.security.ProjectPolicy;
import com.coco.modules.project.domain.ProjectRole;
import com.coco.security.user.CurrentUserService;
import org.springframework.stereotype.Service;

@Service
public class ProjectAuthorizationService {

    private final MembershipRepositoryPort membershipRepo;
    private final MembershipIdResolver roleIds;
    private final CurrentUserService currentUser;

    public ProjectAuthorizationService(
            MembershipRepositoryPort membershipRepo,
            MembershipIdResolver roleIds,
            CurrentUserService currentUser
    ) {
        this.membershipRepo = membershipRepo;
        this.roleIds = roleIds;
        this.currentUser = currentUser;
    }

    public Long currentUserId() {
        return currentUser.getRequiredUserId();
    }

    public ProjectRole requireCurrentUserRole(Long projectId) {
        Long userId = currentUserId();
        var membership = membershipRepo.find(userId, projectId)
                .orElseThrow(() -> new ForbiddenException("No eres un miembro del proyecto"));
        return roleIds.toProjectRole(membership.getRoleId());
    }

    public void requirePermission(Long projectId, ProjectPermission permission) {
        ProjectRole role = requireCurrentUserRole(projectId);
        if (!ProjectPolicy.allows(role, permission)) {
            throw new ForbiddenException("No tienes los suficientes pribilegios, se requiere ser " + permission);
        }
    }
}

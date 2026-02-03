package com.coco.modules.project.application;

import com.coco.common.util.ForbiddenException;
import com.coco.modules.project.application.members.MembershipIdResolver;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.security.user.CurrentUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArchiveProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final MembershipRepositoryPort membershipRepo;
    private final CurrentUserService currentUser;
    private final MembershipIdResolver roleIds;

    public ArchiveProjectUseCase(ProjectRepositoryPort projectRepo,
                                 MembershipRepositoryPort membershipRepo,
                                 CurrentUserService currentUser,
                                 MembershipIdResolver roleIds) {
        this.projectRepo = projectRepo;
        this.membershipRepo = membershipRepo;
        this.currentUser = currentUser;
        this.roleIds = roleIds;
    }

    @Transactional
    public void execute(Long projectId) {
        Long userId = currentUser.getRequiredUserId();

        Membership m = membershipRepo.find(userId, projectId)
                .orElseThrow(() -> new ForbiddenException("Not a project member"));

        if (!m.getRoleId().equals(roleIds.ownerId())) {
            throw new ForbiddenException("Only OWNER can archive project");
        }

        projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));
        projectRepo.archive(projectId);
    }

}

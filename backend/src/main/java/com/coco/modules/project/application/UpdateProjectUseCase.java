package com.coco.modules.project.application;

import com.coco.common.util.ForbiddenException;
import com.coco.modules.project.api.dto.UpdateProjectCommand;
import com.coco.modules.project.application.members.MembershipIdResolver;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.modules.project.domain.Project;
import com.coco.security.user.CurrentUserService;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final MembershipRepositoryPort membershipRepo;
    private final CurrentUserService currentUser;
    private final MembershipIdResolver roleIds;

    public UpdateProjectUseCase(ProjectRepositoryPort projectRepo,
                                MembershipRepositoryPort membershipRepo,
                                CurrentUserService currentUser,
                                MembershipIdResolver roleIds) {
        this.projectRepo = projectRepo;
        this.membershipRepo = membershipRepo;
        this.currentUser = currentUser;
        this.roleIds = roleIds;
    }

    @Transactional
    public Project execute(Long projectId, UpdateProjectCommand cmd) throws ChangeSetPersister.NotFoundException {
        Long userId = currentUser.getRequiredUserId();

        Membership m = membershipRepo.find(userId, projectId)
                .orElseThrow(() -> new ForbiddenException("Not a project member"));

        boolean canEdit = m.getRoleId().equals(roleIds.ownerId()) || m.getRoleId().equals(roleIds.ownerId());
        if (!canEdit) throw new ForbiddenException("Insufficient permissions to edit project");

        Project p = projectRepo.findById(projectId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        p.setName(cmd.name());
        p.setDescription(cmd.description());

        return projectRepo.save(p);
    }
}

package com.coco.modules.project.application;

import com.coco.modules.project.api.dto.CreateProjectCommand;
import com.coco.modules.project.application.members.MembershipIdResolver;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.modules.project.domain.Project;
import com.coco.security.user.CurrentUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class CreateProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final MembershipRepositoryPort membershipRepo;
    private final CurrentUserService currentUser;
    private final MembershipIdResolver roleIdResolver;

    public CreateProjectUseCase(ProjectRepositoryPort projectRepo,
                                MembershipRepositoryPort membershipRepo,
                                CurrentUserService currentUser,
                                MembershipIdResolver roleIdResolver) {
        this.projectRepo = projectRepo;
        this.membershipRepo = membershipRepo;
        this.currentUser = currentUser;
        this.roleIdResolver = roleIdResolver;
    }

    @Transactional
    public Project execute(CreateProjectCommand cmd) {
        Long userId = currentUser.getRequiredUserId();

        Project project = new Project();
        project.setName(cmd.name());
        project.setDescription(cmd.description());
        project.setLogoUrl(cmd.logoUrl());
        project.setStatus("ACTIVE");

        Project created = projectRepo.save(project);

        Long ownerRoleId = roleIdResolver.ownerId();
        membershipRepo.save(new Membership(userId, created.getId(), ownerRoleId));

        return created;
    }
}


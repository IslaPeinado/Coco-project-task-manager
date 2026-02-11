package com.coco.modules.project.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.api.dto.UpdateProjectCommand;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.project.domain.Project;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public UpdateProjectUseCase(ProjectRepositoryPort projectRepo,
                                ProjectAuthorizationService authz) {
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    @Transactional
    public Project execute(Long projectId, UpdateProjectCommand cmd) {
        authz.requirePermission(projectId, ProjectPermission.MANAGE);

        Project p = projectRepo.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found: " + projectId));

        p.setName(cmd.name());
        p.setDescription(cmd.description());

        return projectRepo.save(p);
    }
}

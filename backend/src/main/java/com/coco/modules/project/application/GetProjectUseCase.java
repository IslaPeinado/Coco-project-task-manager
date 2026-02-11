package com.coco.modules.project.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.project.domain.Project;
import org.springframework.stereotype.Service;

@Service
public class GetProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public GetProjectUseCase(ProjectRepositoryPort projectRepo, ProjectAuthorizationService authz) {
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    public Project execute(Long id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found: " + id));
        authz.requirePermission(id, ProjectPermission.READ);
        return project;
    }
}

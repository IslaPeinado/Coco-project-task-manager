package com.coco.modules.project.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArchiveProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public ArchiveProjectUseCase(ProjectRepositoryPort projectRepo,
                                 ProjectAuthorizationService authz) {
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    @Transactional
    public void execute(Long projectId) {
        authz.requirePermission(projectId, ProjectPermission.OWNER_ONLY);

        projectRepo.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found: " + projectId));
        projectRepo.archive(projectId);
    }

}

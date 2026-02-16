package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {

    private final TaskRepositoryPort taskRepo;
    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public DeleteTaskUseCase(TaskRepositoryPort taskRepo,
                             ProjectRepositoryPort projectRepo,
                             ProjectAuthorizationService authz) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    public void execute(Long projectId, Long taskId) {
        ensureProjectExists(projectId);
        authz.requirePermission(projectId, ProjectPermission.WRITE);
        taskRepo.delete(projectId, taskId);
    }

    private void ensureProjectExists(Long projectId) {
        if (projectRepo.findById(projectId).isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
    }
}

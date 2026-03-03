package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Service;

@Service
public class GetTaskUseCase {

    private final TaskRepositoryPort taskRepo;
    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public GetTaskUseCase(TaskRepositoryPort taskRepo,
                          ProjectRepositoryPort projectRepo,
                          ProjectAuthorizationService authz) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    public Task execute(Long projectId, Long taskId) {
        ensureProjectExists(projectId);
        authz.requirePermission(projectId, ProjectPermission.READ);
        return taskRepo.findById(projectId, taskId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));
    }

    private void ensureProjectExists(Long projectId) {
        if (projectRepo.findById(projectId).isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
    }
}

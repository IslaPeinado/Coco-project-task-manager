package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UnassignTaskUseCase {

    private final TaskRepositoryPort taskRepo;
    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public UnassignTaskUseCase(TaskRepositoryPort taskRepo,
                               ProjectRepositoryPort projectRepo,
                               ProjectAuthorizationService authz) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    public Task execute(Long projectId, Long taskId) {
        ensureProjectExists(projectId);
        authz.requirePermission(projectId, ProjectPermission.WRITE);

        Task task = taskRepo.findById(projectId, taskId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        task.setAssignedToId(null);
        task.setUpdatedAt(OffsetDateTime.now());

        return taskRepo.update(projectId, taskId, task);
    }

    private void ensureProjectExists(Long projectId) {
        if (projectRepo.findById(projectId).isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
    }
}

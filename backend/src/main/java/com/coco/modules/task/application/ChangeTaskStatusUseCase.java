package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.application.port.TaskStatusRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ChangeTaskStatusUseCase {

    private final TaskRepositoryPort taskRepo;
    private final TaskStatusRepositoryPort statusRepo;
    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public ChangeTaskStatusUseCase(TaskRepositoryPort taskRepo,
                                   TaskStatusRepositoryPort statusRepo,
                                   ProjectRepositoryPort projectRepo,
                                   ProjectAuthorizationService authz) {
        this.taskRepo = taskRepo;
        this.statusRepo = statusRepo;
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    public Task execute(Long projectId, Long taskId, String status) {
        ensureProjectExists(projectId);
        authz.requirePermission(projectId, ProjectPermission.WRITE);

        Task task = taskRepo.findById(projectId, taskId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        ensureStatusExists(status);
        task.setStatus(status);
        task.setUpdatedAt(OffsetDateTime.now());

        return taskRepo.update(projectId, taskId, task);
    }

    private void ensureProjectExists(Long projectId) {
        if (projectRepo.findById(projectId).isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
    }

    private void ensureStatusExists(String status) {
        if (statusRepo.findById(status).isEmpty()) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}

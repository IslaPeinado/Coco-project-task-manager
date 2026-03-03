package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.api.dto.TaskCreateCommand;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.application.port.TaskStatusRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CreateTaskUseCase {

    private final TaskRepositoryPort taskRepo;
    private final TaskStatusRepositoryPort statusRepo;
    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public CreateTaskUseCase(TaskRepositoryPort taskRepo,
                             TaskStatusRepositoryPort statusRepo,
                             ProjectRepositoryPort projectRepo,
                             ProjectAuthorizationService authz) {
        this.taskRepo = taskRepo;
        this.statusRepo = statusRepo;
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    public Task execute(Long projectId, TaskCreateCommand cmd) {
        ensureProjectExists(projectId);
        authz.requirePermission(projectId, ProjectPermission.WRITE);

        ensureStatusExists(cmd.status());

        Task task = new Task();
        task.setProjectId(projectId);
        task.setTitle(cmd.title());
        task.setDescription(cmd.description());
        task.setStatus(cmd.status());
        task.setDueDate(cmd.dueDate());
        task.setCreatedAt(OffsetDateTime.now());
        task.setUpdatedAt(OffsetDateTime.now());

        return taskRepo.save(task);
    }

    private void ensureProjectExists(Long projectId) {
        if (projectRepo.findById(projectId).isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
    }

    private void ensureStatusExists(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status is required");
        }
        if (statusRepo.findById(status).isEmpty()) {
            throw new NotFoundException("Task status not found: " + status);
        }
    }
}

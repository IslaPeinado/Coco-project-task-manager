package com.coco.modules.task.application;

import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import com.coco.common.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTasksUseCase {

    private final TaskRepositoryPort taskRepo;
    private final ProjectRepositoryPort projectRepo;
    private final ProjectAuthorizationService authz;

    public ListTasksUseCase(TaskRepositoryPort taskRepo,
                            ProjectRepositoryPort projectRepo,
                            ProjectAuthorizationService authz) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.authz = authz;
    }

    public List<Task> execute(Long projectId) {
        ensureProjectExists(projectId);
        authz.requirePermission(projectId, ProjectPermission.READ);
        return taskRepo.findByProjectId(projectId);
    }

    private void ensureProjectExists(Long projectId) {
        if (projectRepo.findById(projectId).isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
    }
}

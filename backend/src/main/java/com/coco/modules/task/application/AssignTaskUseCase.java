package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import com.coco.modules.user.application.port.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class AssignTaskUseCase {

    private final TaskRepositoryPort taskRepo;
    private final ProjectRepositoryPort projectRepo;
    private final UserRepositoryPort userRepo;
    private final MembershipRepositoryPort membershipRepo;
    private final ProjectAuthorizationService authz;

    public AssignTaskUseCase(TaskRepositoryPort taskRepo,
                             ProjectRepositoryPort projectRepo,
                             UserRepositoryPort userRepo,
                             MembershipRepositoryPort membershipRepo,
                             ProjectAuthorizationService authz) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.membershipRepo = membershipRepo;
        this.authz = authz;
    }

    public Task execute(Long projectId, Long taskId, Long userId) {
        ensureProjectExists(projectId);
        authz.requirePermission(projectId, ProjectPermission.WRITE);

        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }

        Task task = taskRepo.findById(projectId, taskId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        if (userRepo.findById(userId).isEmpty()) {
            throw new NotFoundException("User not found: " + userId);
        }
        if (!membershipRepo.exists(userId, projectId)) {
            throw new IllegalArgumentException("User is not a member of project");
        }

        task.setAssignedToId(userId);
        task.setUpdatedAt(OffsetDateTime.now());

        return taskRepo.update(projectId, taskId, task);
    }

    private void ensureProjectExists(Long projectId) {
        if (projectRepo.findById(projectId).isEmpty()) {
            throw new NotFoundException("Project not found: " + projectId);
        }
    }
}

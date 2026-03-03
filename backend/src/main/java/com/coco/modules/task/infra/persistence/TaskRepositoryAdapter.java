package com.coco.modules.task.infra.persistence;

import com.coco.common.util.NotFoundException;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import com.coco.modules.user.infra.persistence.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskJpaRepository taskRepo;
    private final TaskStatusJpaRepository statusRepo;
    private final UserJpaRepository userRepo;

    public TaskRepositoryAdapter(
            TaskJpaRepository taskRepo,
            TaskStatusJpaRepository statusRepo,
            UserJpaRepository userRepo
    ) {
        this.taskRepo = taskRepo;
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        return taskRepo.findByProjectId(projectId).stream()
                .map(TaskEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Task> findById(Long projectId, Long taskId) {
        return taskRepo.findByIdAndProjectId(taskId, projectId)
                .map(TaskEntity::toDomain);
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = TaskEntity.fromDomain(task);
        attachRelations(entity, task);
        return taskRepo.save(entity).toDomain();
    }

    private void attachRelations(TaskEntity entity, Task task) {
        if (task.getStatus() != null) {
            entity.setStatus(statusRepo.getReferenceById(task.getStatus()));
        }
        if (task.getAssignedToId() != null) {
            entity.setAssignedTo(userRepo.getReferenceById(task.getAssignedToId()));
        }
    }

    @Override
    public Task update(Long projectId, Long taskId, Task task) {
        TaskEntity current = taskRepo.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        current.setTitle(task.getTitle());
        current.setDescription(task.getDescription());
        current.setDueDate(task.getDueDate());
        current.setUpdatedAt(task.getUpdatedAt());

        if (task.getStatus() != null) {
            current.setStatus(statusRepo.getReferenceById(task.getStatus()));
        }

        if (task.getAssignedToId() != null) {
            current.setAssignedTo(userRepo.getReferenceById(task.getAssignedToId()));
        } else {
            current.setAssignedTo(null);
        }

        return taskRepo.save(current).toDomain();
    }

    @Override
    public void delete(Long projectId, Long taskId) {
        if (!taskRepo.existsByIdAndProjectId(taskId, projectId)) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        taskRepo.deleteById(taskId);
    }

}

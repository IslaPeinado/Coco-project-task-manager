package com.coco.modules.task.infra.persistence;

import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskJpaRepository taskRepo;
    public TaskRepositoryAdapter(
            TaskJpaRepository taskRepo
    ) {
        this.taskRepo = taskRepo;
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

}

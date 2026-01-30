package com.coco.modules.task.infra.persistence;

import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskJpaRepository taskJpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = new TaskEntity();
        if (task.getId() != null) {
            entity.setId(task.getId());
        }
        entity.setProject(task.getProject());
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setStatus(task.getStatus());
        entity.setAssignedTo(task.getAssignedTo());
        entity.setDueDate(task.getDueDate());
        TaskEntity saved = taskJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Task> findById(Long taskId) {
        return taskJpaRepository.findById(taskId).map(TaskEntity::toDomain);
    }

    @Override
    public void deleteById(Long taskId) {
        taskJpaRepository.deleteById(taskId);
    }
}

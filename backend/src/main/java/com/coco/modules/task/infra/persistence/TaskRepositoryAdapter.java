package com.coco.modules.task.infra.persistence;

import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskJpaRepository taskJpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setStatus(task.getStatus());
        entity.setDueDate(task.getDueDate());
        entity.setProject(task.getProject());
        taskJpaRepository.save(entity);
        return task;
    }
}

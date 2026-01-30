package com.coco.modules.task.infra.persistence;

import com.coco.modules.task.application.port.TaskStatusRepositoryPort;
import com.coco.modules.task.domain.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskStatusRepositoryAdapter implements TaskStatusRepositoryPort {

    private final TaskStatusJpaRepository taskStatusJpaRepository;

    public TaskStatusRepositoryAdapter(TaskStatusJpaRepository taskStatusJpaRepository) {
        this.taskStatusJpaRepository = taskStatusJpaRepository;
    }

    @Override
    public TaskStatus save(TaskStatus taskStatus) {
        TaskStatusEntity entity = new TaskStatusEntity();
        if (taskStatus.getStatus() != null) {
            entity.setStatus(taskStatus.getStatus());
        }
        entity.setDisplayName(taskStatus.getDisplayName());
        entity.setColorHex(taskStatus.getColorHex());
        entity.setSortOrder(taskStatus.getSortOrder());
        entity.setIsTerminal(taskStatus.getIsTerminal());
        TaskStatusEntity saved = taskStatusJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<TaskStatus> findById(String status) {
        return taskStatusJpaRepository.findById(Long.valueOf(status)).map(TaskStatusEntity::toDomain);
    }

    @Override
    public void deleteById(String status) {

    }
}

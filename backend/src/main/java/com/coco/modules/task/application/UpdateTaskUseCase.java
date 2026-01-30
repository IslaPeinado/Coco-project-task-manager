package com.coco.modules.task.application;


import com.coco.modules.task.api.dto.TaskUpdateRequest;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public UpdateTaskUseCase(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    public Task execute(Long taskId, TaskUpdateRequest request) {
        Task task = taskRepositoryPort.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setAssignedTo(request.getAssigned_to());
        task.setDueDate(request.getDueDate());

        return taskRepositoryPort.save(task);
    }
}

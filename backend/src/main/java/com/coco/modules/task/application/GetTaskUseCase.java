package com.coco.modules.task.application;


import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.springframework.stereotype.Service;

@Service
public class GetTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public GetTaskUseCase(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    public Task execute(Long taskId) {
        return taskRepositoryPort.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }
}

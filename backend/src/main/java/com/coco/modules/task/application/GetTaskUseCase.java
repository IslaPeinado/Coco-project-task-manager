package com.coco.modules.task.application;


import com.coco.modules.task.application.port.TaskRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public GetTaskUseCase(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

}

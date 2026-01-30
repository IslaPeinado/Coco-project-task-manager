package com.coco.modules.task.application;

import com.coco.modules.task.domain.Task;
import com.coco.modules.task.api.dto.TaskCreateRequest;
import com.coco.modules.task.api.mapper.TaskApiMapper;

import org.springframework.stereotype.Service;

@Service
public class CreateTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;
    private final ProjectRepositoryPort projectRepositoryPort;

    public CreateTaskUseCase(TaskRepositoryPort taskRepositoryPort, ProjectRepositoryPort projectRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.projectRepositoryPort = projectRepositoryPort;
    }

    public Task execute(TaskCreateRequest request) {
        // Obtener el proyecto asociado
        Project project = projectRepositoryPort.findById(request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // Mapear el request al dominio
        Task task = TaskApiMapper.toDomain(request, project);

        // Guardar la tarea
        return taskRepositoryPort.save(task);
    }
}

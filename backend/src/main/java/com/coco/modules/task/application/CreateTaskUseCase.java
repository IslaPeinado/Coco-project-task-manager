package com.coco.modules.task.application;


import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.modules.task.api.dto.TaskCreateRequest;
import com.coco.modules.task.api.mapper.TaskApiMapper;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.application.port.TaskStatusRepositoryPort;
import com.coco.modules.task.domain.Task;
import com.coco.modules.task.domain.TaskStatus;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class CreateTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;
    private final TaskStatusRepositoryPort taskStatusRepositoryPort;
    private final ProjectRepositoryPort projectRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;


    public CreateTaskUseCase(TaskRepositoryPort taskRepositoryPort, ProjectRepositoryPort projectRepositoryPort, TaskStatusRepositoryPort taskStatusRepositoryPort, UserRepositoryPort userRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.taskStatusRepositoryPort = taskStatusRepositoryPort;
        this.projectRepositoryPort = projectRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    public Task execute(TaskCreateRequest request) {
        // Obtener el proyecto asociado
        Project project = projectRepositoryPort.findById(request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // Obtener el estado
        TaskStatus status = taskStatusRepositoryPort.findById(request.getStatus())
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));

        //Obtener el usuario asociado
        User user = userRepositoryPort.findById(request.getAssigned_to())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Mapear el request al dominio
        Task task = TaskApiMapper.toDomain(request, project, status, user);

        // Guardar la tarea
        return taskRepositoryPort.save(task);
    }
}

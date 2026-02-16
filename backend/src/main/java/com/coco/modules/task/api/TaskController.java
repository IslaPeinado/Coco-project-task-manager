package com.coco.modules.task.api;


import com.coco.modules.task.api.dto.*;
import com.coco.modules.task.application.CreateTaskUseCase;
import com.coco.modules.task.application.GetTaskUseCase;
import com.coco.modules.task.application.ListTasksUseCase;
import com.coco.modules.task.application.UpdateTaskUseCase;
import com.coco.modules.task.domain.Task;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ListTasksUseCase listTasks;
    private final GetTaskUseCase getTask;
    private final CreateTaskUseCase createTask;
    private final UpdateTaskUseCase updateTask;


    @GetMapping
    public List<TaskResponse> list(@PathVariable Long projectId) {
        return listTasks.execute(projectId).stream()
                .map(TaskController::toResponse)
                .toList();
    }

    @GetMapping("/{taskId}")
    public TaskResponse get(@PathVariable Long projectId, @PathVariable Long taskId) {
        return toResponse(getTask.execute(projectId, taskId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@PathVariable Long projectId, @Valid @RequestBody TaskCreateRequest request) {
        var cmd = new TaskCreateCommand(
                request.title(),
                request.description(),
                request.status(),
                request.dueDate()
        );
        return toResponse(createTask.execute(projectId, cmd));
    }

    @PutMapping("/{taskId}")
    public TaskResponse update(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody TaskUpdateRequest request) {
        var cmd = new TaskUpdateCommand(
                request.title(),
                request.description(),
                request.status(),
                request.dueDate()
        );
        return toResponse(updateTask.execute(projectId, taskId, cmd));
    }


    private static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getProjectId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignedToId(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}

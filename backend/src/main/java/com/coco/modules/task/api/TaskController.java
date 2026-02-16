package com.coco.modules.task.api;


import com.coco.modules.task.api.dto.TaskResponse;
import com.coco.modules.task.application.ListTasksUseCase;
import com.coco.modules.task.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ListTasksUseCase listTasks;


    @GetMapping
    public List<TaskResponse> list(@PathVariable Long projectId) {
        return listTasks.execute(projectId).stream()
                .map(TaskController::toResponse)
                .toList();
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

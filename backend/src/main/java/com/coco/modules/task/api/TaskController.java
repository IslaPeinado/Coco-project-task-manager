package com.coco.modules.task.api;

import com.coco.modules.task.api.dto.TaskCreateRequest;
import com.coco.modules.task.application.CreateTaskUseCase;
import com.coco.modules.task.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskCreateRequest request) {
        return createTaskUseCase.execute(request);
    }
}

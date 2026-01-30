package com.coco.modules.task.api;

import com.coco.modules.task.api.dto.TaskCreateRequest;
import com.coco.modules.task.application.CreateTaskUseCase;
import com.coco.modules.task.application.GetTaskUseCase;
import com.coco.modules.task.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase, GetTaskUseCase getTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.getTaskUseCase = getTaskUseCase;
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskCreateRequest request) {
        return createTaskUseCase.execute(request);
    }

    //GET
    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable Long taskId) {
        return getTaskUseCase.execute(taskId);
    }

}

package com.coco.modules.task.api.mapper;

import com.coco.modules.task.api.dto.TaskCreateRequest;
import com.coco.modules.task.domain.Task;
import com.coco.modules.task.domain.TaskStatus;
import org.mapstruct.Mapper;

@Mapper
public class TaskApiMapper {

    public static Task toDomain(TaskCreateRequest request, Project project) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        task.setDueDate(request.getDueDate());
        task.setProject(project);
        return task;
    }
}

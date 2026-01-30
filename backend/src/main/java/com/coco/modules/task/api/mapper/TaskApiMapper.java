package com.coco.modules.task.api.mapper;

import com.coco.modules.project.domain.Project;
import com.coco.modules.task.api.dto.TaskCreateRequest;
import com.coco.modules.task.domain.Task;
import com.coco.modules.task.domain.TaskStatus;
import com.coco.modules.user.domain.User;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper
public class TaskApiMapper {

    public static Task toDomain(TaskCreateRequest request, Project project, TaskStatus status, User user) {
        Task task = new Task();
        task.setProject(project);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(status);
        task.setAssignedTo(user);
        task.setDueDate(LocalDate.from(request.getDueDate()));
        return task;
    }
}

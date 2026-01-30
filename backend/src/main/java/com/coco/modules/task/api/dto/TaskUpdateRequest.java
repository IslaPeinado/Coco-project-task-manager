package com.coco.modules.task.api.dto;

import com.coco.modules.task.domain.TaskStatus;
import com.coco.modules.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class TaskUpdateRequest {

    private String title;
    private String description;
    private TaskStatus status;
    private User assigned_to;
    private LocalDate dueDate;

}
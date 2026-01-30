package com.coco.modules.task.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskCreateRequest {

    private Long projectId;
    private String title;
    private String description;
    private String status;
    private Long assigned_to;
    private LocalDateTime dueDate;

}

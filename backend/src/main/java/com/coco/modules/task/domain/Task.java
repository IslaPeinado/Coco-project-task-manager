package com.coco.modules.task.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Task {

    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private String status;
    private Long assignedToId;
    private LocalDate dueDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

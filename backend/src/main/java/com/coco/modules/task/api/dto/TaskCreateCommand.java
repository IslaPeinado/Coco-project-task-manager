package com.coco.modules.task.api.dto;

import java.time.LocalDate;

public record TaskCreateCommand(
        String title,
        String description,
        String status,
        LocalDate dueDate
) { }

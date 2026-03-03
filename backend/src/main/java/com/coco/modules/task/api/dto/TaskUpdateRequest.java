package com.coco.modules.task.api.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskUpdateRequest(
        @Size(min = 1, max = 255)
        String title,
        String description,
        @Size(max = 50)
        String status,
        LocalDate dueDate
) { }

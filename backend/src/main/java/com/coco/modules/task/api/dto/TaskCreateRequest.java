package com.coco.modules.task.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskCreateRequest(
        @NotBlank
        @Size(max = 255)
        String title,
        String description,
        @NotBlank
        @Size(max = 50)
        String status,
        LocalDate dueDate
) { }

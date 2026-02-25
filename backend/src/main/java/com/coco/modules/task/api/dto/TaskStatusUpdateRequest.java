package com.coco.modules.task.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskStatusUpdateRequest(
        @NotBlank
        @Size(max = 50)
        String status
) {
}

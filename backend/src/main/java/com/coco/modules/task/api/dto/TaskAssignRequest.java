package com.coco.modules.task.api.dto;

import jakarta.validation.constraints.NotNull;

public record TaskAssignRequest(
        @NotNull Long userId
) {
}

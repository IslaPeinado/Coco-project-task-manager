package com.coco.modules.project.api.dto;

import jakarta.validation.constraints.NotNull;

public record AddMemberRequest(
        @NotNull Long userId,
        @NotNull Long roleId
) { }

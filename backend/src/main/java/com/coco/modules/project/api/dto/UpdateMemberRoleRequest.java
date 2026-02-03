package com.coco.modules.project.api.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull Long roleId
) { }

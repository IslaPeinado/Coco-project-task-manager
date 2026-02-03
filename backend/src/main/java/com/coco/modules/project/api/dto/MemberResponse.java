package com.coco.modules.project.api.dto;

public record MemberResponse(
        Long userId,
        Long projectId,
        Long roleId
) { }

package com.coco.modules.project.api.dto;

import com.coco.modules.project.domain.ProjectStatus;

import java.time.OffsetDateTime;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        String logoUrl,
        ProjectStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime archivedAt
) { }

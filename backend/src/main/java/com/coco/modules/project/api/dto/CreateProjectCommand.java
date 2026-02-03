package com.coco.modules.project.api.dto;

public record CreateProjectCommand(
        String name,
        String description,
        String logoUrl
) { }

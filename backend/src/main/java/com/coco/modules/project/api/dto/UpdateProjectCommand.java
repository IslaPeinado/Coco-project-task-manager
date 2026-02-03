package com.coco.modules.project.api.dto;


public record UpdateProjectCommand(
        String name,
        String description,
        String logoUrl
) { }

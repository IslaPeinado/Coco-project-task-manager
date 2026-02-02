package com.coco.modules.project.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectUpdateRequest(
        @NotBlank String name,
        String description,
        String logoUrl
) { }

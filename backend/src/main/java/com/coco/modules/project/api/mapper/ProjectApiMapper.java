package com.coco.modules.project.api.mapper;

import com.coco.modules.project.api.dto.ProjectResponse;
import com.coco.modules.project.domain.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectApiMapper {
    public ProjectResponse toResponse(Project p) {
        return new ProjectResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getLogoUrl(),
                p.getStatus(),
                p.getCreatedAt(),
                p.getUpdatedAt(),
                p.getArchivedAt());
    }
}

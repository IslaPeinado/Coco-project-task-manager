package com.coco.modules.project.application;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UpdateProjectUseCase {

    private final ProjectRepositoryPort repo;

    public UpdateProjectUseCase(ProjectRepositoryPort repo) {
        this.repo = repo;
    }

    public Project execute(Long id, Project project) {
        // No permitimos cambiar status/archivedAt desde aquí
        project.setId(id);
        project.setUpdatedAt(OffsetDateTime.now());
        return repo.update(id, project);
    }
}

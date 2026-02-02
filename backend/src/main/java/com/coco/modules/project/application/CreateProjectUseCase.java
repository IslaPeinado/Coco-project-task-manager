package com.coco.modules.project.application;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.modules.project.domain.ProjectStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CreateProjectUseCase {

    private final ProjectRepositoryPort repo;

    public CreateProjectUseCase(ProjectRepositoryPort repo) {
        this.repo = repo;
    }

    public Project execute(Project project) {
        // Forzamos reglas MVP
        project.setId(null);
        project.setStatus(ProjectStatus.ACTIVE.name());
        project.setArchivedAt(null);
        project.setCreatedAt(OffsetDateTime.now());
        project.setUpdatedAt(OffsetDateTime.now());
        return repo.save(project);
    }
}

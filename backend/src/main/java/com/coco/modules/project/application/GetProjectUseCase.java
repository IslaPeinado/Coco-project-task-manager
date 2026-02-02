package com.coco.modules.project.application;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import org.springframework.stereotype.Service;

@Service
public class GetProjectUseCase {

    private final ProjectRepositoryPort projectRepo;

    public GetProjectUseCase(ProjectRepositoryPort projectRepo) {
        this.projectRepo = projectRepo;
    }

    public Project execute(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));
    }
}

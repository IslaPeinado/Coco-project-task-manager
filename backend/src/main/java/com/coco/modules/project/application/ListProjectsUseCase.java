package com.coco.modules.project.application;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProjectsUseCase {

    private final ProjectRepositoryPort projectRepo;

    public ListProjectsUseCase(ProjectRepositoryPort projectRepo) {
        this.projectRepo = projectRepo;
    }

    public List<Project> execute(boolean includeArchived) {
        return projectRepo.findAll(includeArchived);
    }
}

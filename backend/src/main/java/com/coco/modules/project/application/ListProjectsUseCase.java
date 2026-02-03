package com.coco.modules.project.application;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.security.user.CurrentUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProjectsUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final CurrentUserService currentUser;

    public ListProjectsUseCase(ProjectRepositoryPort projectRepo, CurrentUserService currentUser) {
        this.projectRepo = projectRepo;
        this.currentUser = currentUser;
    }

    public List<Project> execute(boolean includeArchived) {
        Long userId = currentUser.getRequiredUserId();
        return projectRepo.findAccessibleByUser(userId, includeArchived);
    }
}


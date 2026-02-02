package com.coco.modules.project.api;

import com.coco.modules.project.api.dto.ProjectResponse;
import com.coco.modules.project.application.GetProjectUseCase;
import com.coco.modules.project.application.ListProjectsUseCase;
import com.coco.modules.project.domain.Project;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ListProjectsUseCase listProjects;
    private final GetProjectUseCase getProject;

    public ProjectController(ListProjectsUseCase listProjects, GetProjectUseCase getProject) {
        this.listProjects = listProjects;
        this.getProject = getProject;
    }

    @GetMapping
    public List<ProjectResponse> list(@RequestParam(defaultValue = "false") boolean includeArchived) {
        return listProjects.execute(includeArchived).stream()
                .map(ProjectController::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProjectResponse get(@PathVariable Long id) {
        return toResponse(getProject.execute(id));
    }

    private static ProjectResponse toResponse(Project p) {
        return new ProjectResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getLogoUrl(),
                p.getStatus(),
                p.getCreatedAt(),
                p.getUpdatedAt(),
                p.getArchivedAt()
        );
    }
}

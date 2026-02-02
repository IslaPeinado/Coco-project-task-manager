package com.coco.modules.project.api;

import com.coco.modules.project.api.dto.ProjectCreateRequest;
import com.coco.modules.project.api.dto.ProjectResponse;
import com.coco.modules.project.api.dto.ProjectUpdateRequest;
import com.coco.modules.project.application.CreateProjectUseCase;
import com.coco.modules.project.application.GetProjectUseCase;
import com.coco.modules.project.application.ListProjectsUseCase;
import com.coco.modules.project.application.UpdateProjectUseCase;
import com.coco.modules.project.domain.Project;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ListProjectsUseCase listProjects;
    private final GetProjectUseCase getProject;
    private final CreateProjectUseCase create;
    private final UpdateProjectUseCase update;


    public ProjectController(ListProjectsUseCase listProjects, GetProjectUseCase getProject, CreateProjectUseCase create, UpdateProjectUseCase update) {
        this.listProjects = listProjects;
        this.getProject = getProject;
        this.create = create;
        this.update = update;
    }

    // GET
    @GetMapping
    public List<ProjectResponse> list(@RequestParam(defaultValue = "false") boolean includeArchived) {
        return listProjects.execute(includeArchived).stream()
                .map(ProjectController::toResponse)
                .toList();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ProjectResponse get(@PathVariable Long id) {
        return toResponse(getProject.execute(id));
    }

    // POST
    @PostMapping
    public ProjectResponse create(@Valid @RequestBody ProjectCreateRequest req) {
        Project p = new Project();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setLogoUrl(req.logoUrl());
        return toResponse(create.execute(p));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable Long id, @Valid @RequestBody ProjectUpdateRequest req) {
        Project p = new Project();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setLogoUrl(req.logoUrl());
        return toResponse(update.execute(id, p));
    }

    private static ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getLogoUrl(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getArchivedAt()
        );
    }
}

package com.coco.modules.project.api;

import com.coco.modules.project.api.dto.*;
import com.coco.modules.project.application.*;
import com.coco.modules.project.domain.Project;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ListProjectsUseCase listProjects;
    private final GetProjectUseCase getProject;
    private final CreateProjectUseCase create;
    private final UpdateProjectUseCase update;
    private final ArchiveProjectUseCase archive;


    public ProjectController(ListProjectsUseCase listProjects, GetProjectUseCase getProject, CreateProjectUseCase create, UpdateProjectUseCase update, ArchiveProjectUseCase archive) {
        this.listProjects = listProjects;
        this.getProject = getProject;
        this.create = create;
        this.update = update;
        this.archive = archive;
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
        var cmd = new CreateProjectCommand(
                req.name(),
                req.description(),
                req.logoUrl()
        );
        return toResponse(create.execute(cmd));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable Long id, @Valid @RequestBody ProjectUpdateRequest req) throws ChangeSetPersister.NotFoundException {
        var cmd = new UpdateProjectCommand(
                req.name(),
                req.description(),
                req.logoUrl()
        );
        return toResponse(update.execute(id, cmd));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archive(@PathVariable Long id) {
        archive.execute(id);
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

package com.coco.modules.project.application.port;

import com.coco.modules.project.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryPort {

    List<Project> findAll(boolean includeArchived);

    Optional<Project> findById(Long id);

    Project save(Project project);

    Project update(Long id, Project project);

    void archive(Long id);
}

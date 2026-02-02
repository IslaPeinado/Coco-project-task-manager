package com.coco.modules.project.infra.persistence;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryAdapter implements ProjectRepositoryPort {

    private final ProjectJpaRepository repo;

    public ProjectRepositoryAdapter(ProjectJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Project> findAll(boolean includeArchived) {
        List<ProjectEntity> entities = includeArchived
                ? repo.findAllOrdered()
                : repo.findAllActive();

        return entities.stream()
                .map(ProjectEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return repo.findById(id).map(ProjectEntity::toDomain);
    }

    @Override
    public Project save(Project project) {
        var entity = ProjectEntity.fromDomain(project);
        return repo.save(entity).toDomain();
    }

    @Override
    public Project update(Long id, Project project) {
        var current = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));

        // Campos editables
        current.setName(project.getName());
        current.setDescription(project.getDescription());
        current.setLogoUrl(project.getLogoUrl());
        current.setUpdatedAt(project.getUpdatedAt());

        return repo.save(current).toDomain();
    }
}
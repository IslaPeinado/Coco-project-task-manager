package com.coco.modules.project.infra.persistence;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProjectRepositoryAdapter implements ProjectRepositoryPort {

    private final ProjectJpaRepository projectJpaRepository;

    public ProjectRepositoryAdapter(ProjectJpaRepository projectJpaRepository){
        this.projectJpaRepository = projectJpaRepository;
    }

    @Override
    public Project save(Project project) {
        ProjectEntity entity = new ProjectEntity();
        if (project.getId() != null){
            entity.setId(project.getId());
        }
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        entity.setLogoUrl(project.getLogoUrl());
        entity.setStatus(project.getStatus());
        ProjectEntity saved = projectJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectJpaRepository.findById(id).map(ProjectEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        projectJpaRepository.deleteById(id);
    }
}
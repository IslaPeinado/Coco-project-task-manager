package com.coco.modules.task.application.port;

public interface ProjectRepositoryPort {

    // Obtener un proyecto por su ID
    Optional<Project> findById(Long projectId);
}

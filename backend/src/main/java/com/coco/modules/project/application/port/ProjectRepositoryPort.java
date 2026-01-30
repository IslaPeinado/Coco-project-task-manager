package com.coco.modules.project.application.port;

import com.coco.modules.project.domain.Project;
import java.util.Optional;

public interface ProjectRepositoryPort {

    // Guardar un nuevo proyecto o actualizar uno existente
    Project save(Project project);

    // Obtener un proyecto por su ID
    Optional<Project> findById(Long id);

    // Eliminar un proyectopor su ID
    void deleteById(Long id);
}
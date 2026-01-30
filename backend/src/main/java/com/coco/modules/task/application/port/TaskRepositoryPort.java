package com.coco.modules.task.application.port;

import com.coco.modules.task.domain.Task;

import java.util.Optional;


public interface TaskRepositoryPort {

    // Guardar una nueva tarea o actualizar una existente
    Task save(Task task);

    // Obtener una tarea por su ID
    Optional<Task> findById(Long taskId);

    // Eliminar una tarea por su ID
    void deleteById(Long taskId);
}

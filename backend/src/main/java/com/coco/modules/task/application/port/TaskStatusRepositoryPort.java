package com.coco.modules.task.application.port;

import com.coco.modules.task.domain.TaskStatus;

import java.util.Optional;


public interface TaskStatusRepositoryPort {

    // Guardar una nueva tarea o actualizar una existente
    TaskStatus save(TaskStatus status);

    // Obtener una tarea por su ID
    Optional<TaskStatus> findById(String statusId);


    // Eliminar una tarea por su ID
    void deleteById(String statusId);
}

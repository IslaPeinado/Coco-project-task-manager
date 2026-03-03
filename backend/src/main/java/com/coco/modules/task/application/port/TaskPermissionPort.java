package com.coco.modules.task.application.port;

public interface TaskPermissionPort {

    // Verificar si un usuario tiene permisos sobre un proyecto
    boolean hasPermission(Long userId, Long projectId);
}

package com.coco.modules.task.application.port;

import com.coco.modules.task.domain.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {

    List<Task> findByProjectId(Long projectId);

    Optional<Task> findById(Long projectId, Long taskId);

    Task save(Task task);

    Task update(Long projectId, Long taskId, Task task);

    void delete(Long projectId, Long taskId);
}

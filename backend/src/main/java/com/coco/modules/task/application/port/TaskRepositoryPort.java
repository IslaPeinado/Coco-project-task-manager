package com.coco.modules.task.application.port;

import com.coco.modules.task.domain.Task;
import java.util.List;

public interface TaskRepositoryPort {

    List<Task> findByProjectId(Long projectId);

}

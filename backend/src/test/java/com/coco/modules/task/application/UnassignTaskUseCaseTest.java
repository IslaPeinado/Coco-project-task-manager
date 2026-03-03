package com.coco.modules.task.application;

import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnassignTaskUseCaseTest {

    @Mock
    private TaskRepositoryPort taskRepo;
    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private UnassignTaskUseCase useCase;

    @Test
    void execute_removesAssignedUserFromTask() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setId(99L);
        task.setProjectId(10L);
        task.setAssignedToId(7L);
        when(taskRepo.findById(10L, 99L)).thenReturn(Optional.of(task));
        when(taskRepo.update(10L, 99L, task)).thenReturn(task);

        useCase.execute(10L, 99L);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepo).update(org.mockito.ArgumentMatchers.eq(10L), org.mockito.ArgumentMatchers.eq(99L), taskCaptor.capture());
        Task persisted = taskCaptor.getValue();

        assertNull(persisted.getAssignedToId());
        assertNotNull(persisted.getUpdatedAt());
        verify(authz).requirePermission(10L, ProjectPermission.WRITE);
    }
}

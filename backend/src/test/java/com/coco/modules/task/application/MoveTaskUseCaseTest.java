package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.application.port.TaskStatusRepositoryPort;
import com.coco.modules.task.domain.Task;
import com.coco.modules.task.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoveTaskUseCaseTest {

    @Mock
    private TaskRepositoryPort taskRepo;
    @Mock
    private TaskStatusRepositoryPort statusRepo;
    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private MoveTaskUseCase useCase;

    @Test
    void execute_movesTaskToCatalogStatus() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setId(99L);
        task.setProjectId(10L);
        task.setStatus("TODO");
        when(taskRepo.findById(10L, 99L)).thenReturn(Optional.of(task));

        TaskStatus status = new TaskStatus();
        status.setStatus("IN_PROGRESS");
        when(statusRepo.findById("IN_PROGRESS")).thenReturn(Optional.of(status));
        when(taskRepo.update(10L, 99L, task)).thenReturn(task);

        Task result = useCase.execute(10L, 99L, "IN_PROGRESS");

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepo).update(eq(10L), eq(99L), taskCaptor.capture());
        Task persisted = taskCaptor.getValue();

        assertEquals("IN_PROGRESS", persisted.getStatus());
        assertNotNull(persisted.getUpdatedAt());
        assertEquals("IN_PROGRESS", result.getStatus());
        verify(authz).requirePermission(10L, ProjectPermission.WRITE);
    }

    @Test
    void execute_throwsWhenStatusDoesNotExistInCatalog() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setId(99L);
        when(taskRepo.findById(10L, 99L)).thenReturn(Optional.of(task));
        when(statusRepo.findById("INVALID")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(10L, 99L, "INVALID"));
    }
}

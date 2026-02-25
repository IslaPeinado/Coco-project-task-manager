package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.project.domain.Project;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.application.port.TaskStatusRepositoryPort;
import com.coco.modules.task.domain.Task;
import com.coco.modules.task.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangeTaskStatusUseCaseTest {

    @Mock
    private TaskRepositoryPort taskRepo;
    @Mock
    private TaskStatusRepositoryPort statusRepo;
    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private ChangeTaskStatusUseCase useCase;

    @Test
    void execute_updatesTaskStatus() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task current = new Task();
        current.setId(5L);
        current.setProjectId(10L);
        current.setStatus("TODO");
        when(taskRepo.findById(10L, 5L)).thenReturn(Optional.of(current));

        TaskStatus inProgress = new TaskStatus();
        inProgress.setStatus("IN_PROGRESS");
        when(statusRepo.findById("IN_PROGRESS")).thenReturn(Optional.of(inProgress));

        when(taskRepo.update(any(), any(), any())).thenAnswer(invocation -> invocation.getArgument(2, Task.class));

        Task updated = useCase.execute(10L, 5L, "IN_PROGRESS");

        assertEquals("IN_PROGRESS", updated.getStatus());
        assertNotNull(updated.getUpdatedAt());
        verify(authz).requirePermission(10L, ProjectPermission.WRITE);
    }

    @Test
    void execute_whenProjectDoesNotExist_throwsNotFound() {
        when(projectRepo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(10L, 5L, "IN_PROGRESS"));
        verify(taskRepo, never()).update(any(), any(), any());
    }

    @Test
    void execute_whenTaskDoesNotExist_throwsNotFound() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));
        when(taskRepo.findById(10L, 5L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(10L, 5L, "IN_PROGRESS"));
        verify(taskRepo, never()).update(any(), any(), any());
    }

    @Test
    void execute_whenStatusIsInvalid_throwsBadRequest() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task current = new Task();
        current.setId(5L);
        current.setProjectId(10L);
        current.setStatus("TODO");
        when(taskRepo.findById(10L, 5L)).thenReturn(Optional.of(current));
        when(statusRepo.findById("INVALID")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(10L, 5L, "INVALID"));
        verify(taskRepo, never()).update(any(), any(), any());
    }
}

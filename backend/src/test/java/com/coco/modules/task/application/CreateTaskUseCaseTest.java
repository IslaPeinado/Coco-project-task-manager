package com.coco.modules.task.application;

import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.modules.task.api.dto.TaskCreateCommand;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {

    @Mock
    private TaskRepositoryPort taskRepo;
    @Mock
    private TaskStatusRepositoryPort statusRepo;
    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private CreateTaskUseCase useCase;

    @Test
    void execute_createsTaskInProject() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        TaskStatus status = new TaskStatus();
        status.setStatus("TODO");
        when(statusRepo.findById("TODO")).thenReturn(Optional.of(status));

        when(taskRepo.save(any(Task.class))).thenAnswer(invocation -> {
            Task saved = invocation.getArgument(0, Task.class);
            saved.setId(99L);
            return saved;
        });

        Task result = useCase.execute(10L, new TaskCreateCommand("Task 1", "desc", "TODO", null));

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepo).save(taskCaptor.capture());
        Task persisted = taskCaptor.getValue();

        assertEquals(10L, persisted.getProjectId());
        assertEquals("Task 1", persisted.getTitle());
        assertEquals("desc", persisted.getDescription());
        assertEquals("TODO", persisted.getStatus());
        assertNotNull(persisted.getCreatedAt());
        assertNotNull(persisted.getUpdatedAt());

        assertEquals(99L, result.getId());
    }
}

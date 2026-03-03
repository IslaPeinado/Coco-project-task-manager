package com.coco.modules.task.application;

import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTaskUseCaseTest {

    @Mock
    private TaskRepositoryPort taskRepo;
    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private GetTaskUseCase useCase;

    @Test
    void execute_requiresReadPermission() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setId(5L);
        when(taskRepo.findById(10L, 5L)).thenReturn(Optional.of(task));

        Task result = useCase.execute(10L, 5L);

        assertEquals(5L, result.getId());
        verify(authz).requirePermission(10L, ProjectPermission.READ);
    }
}

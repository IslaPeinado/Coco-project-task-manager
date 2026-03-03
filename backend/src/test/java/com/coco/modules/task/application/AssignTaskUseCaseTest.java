package com.coco.modules.task.application;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Project;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.task.application.port.TaskRepositoryPort;
import com.coco.modules.task.domain.Task;
import com.coco.modules.user.application.port.UserRepositoryPort;
import com.coco.modules.user.domain.User;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignTaskUseCaseTest {

    @Mock
    private TaskRepositoryPort taskRepo;
    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private UserRepositoryPort userRepo;
    @Mock
    private MembershipRepositoryPort membershipRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private AssignTaskUseCase useCase;

    @Test
    void execute_assignsUserToTask() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setId(99L);
        task.setProjectId(10L);
        task.setTitle("Task 1");
        task.setStatus("TODO");
        when(taskRepo.findById(10L, 99L)).thenReturn(Optional.of(task));

        User user = new User();
        user.setId(7L);
        when(userRepo.findById(7L)).thenReturn(Optional.of(user));
        when(membershipRepo.exists(7L, 10L)).thenReturn(true);
        when(taskRepo.update(10L, 99L, task)).thenReturn(task);

        Task result = useCase.execute(10L, 99L, 7L);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepo).update(org.mockito.ArgumentMatchers.eq(10L), org.mockito.ArgumentMatchers.eq(99L), taskCaptor.capture());
        Task persisted = taskCaptor.getValue();

        assertEquals(7L, persisted.getAssignedToId());
        assertNotNull(persisted.getUpdatedAt());
        assertEquals(7L, result.getAssignedToId());
        verify(authz).requirePermission(10L, ProjectPermission.WRITE);
    }

    @Test
    void execute_throwsWhenUserDoesNotExist() {
        Project project = new Project();
        project.setId(10L);
        when(projectRepo.findById(10L)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setId(99L);
        when(taskRepo.findById(10L, 99L)).thenReturn(Optional.of(task));
        when(userRepo.findById(7L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(10L, 99L, 7L));
    }
}

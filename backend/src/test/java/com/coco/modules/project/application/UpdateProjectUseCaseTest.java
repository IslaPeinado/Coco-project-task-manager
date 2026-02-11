package com.coco.modules.project.application;

import com.coco.common.util.ForbiddenException;
import com.coco.modules.project.api.dto.UpdateProjectCommand;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.project.domain.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateProjectUseCaseTest {

    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private UpdateProjectUseCase useCase;

    @Test
    void execute_updatesProjectWhenCurrentUserIsOwner() {
        Long projectId = 20L;

        Project project = new Project();
        project.setId(projectId);
        project.setName("before");
        project.setDescription("before-desc");

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepo.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0, Project.class));

        Project result = useCase.execute(projectId, new UpdateProjectCommand("after", "after-desc", null));

        assertEquals("after", result.getName());
        assertEquals("after-desc", result.getDescription());
        verify(projectRepo).save(project);
    }

    @Test
    void execute_throwsForbiddenWhenCurrentUserRoleCannotEdit() {
        Long projectId = 20L;

        doThrow(new ForbiddenException("Insufficient permissions"))
                .when(authz).requirePermission(projectId, ProjectPermission.MANAGE);

        assertThrows(ForbiddenException.class, () -> useCase.execute(projectId, new UpdateProjectCommand("x", "y", null)));
        verify(projectRepo, never()).save(any(Project.class));
    }
}

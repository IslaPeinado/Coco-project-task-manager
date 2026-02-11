package com.coco.modules.project.application;

import com.coco.common.util.ForbiddenException;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import com.coco.modules.project.domain.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchiveProjectUseCaseTest {

    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private ProjectAuthorizationService authz;

    @InjectMocks
    private ArchiveProjectUseCase useCase;

    @Test
    void execute_archivesProjectWhenCurrentUserIsOwner() {
        Long projectId = 99L;

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(new Project()));

        useCase.execute(projectId);

        verify(projectRepo).archive(projectId);
    }

    @Test
    void execute_throwsForbiddenWhenCurrentUserIsNotOwner() {
        Long projectId = 99L;

        doThrow(new ForbiddenException("Only OWNER can archive project"))
                .when(authz).requirePermission(projectId, ProjectPermission.OWNER_ONLY);

        assertThrows(ForbiddenException.class, () -> useCase.execute(projectId));
        verify(projectRepo, never()).archive(projectId);
    }
}

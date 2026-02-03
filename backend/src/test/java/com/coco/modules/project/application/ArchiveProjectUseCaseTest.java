package com.coco.modules.project.application;

import com.coco.common.util.ForbiddenException;
import com.coco.modules.project.application.members.MembershipIdResolver;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.modules.project.domain.Project;
import com.coco.security.user.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchiveProjectUseCaseTest {

    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private MembershipRepositoryPort membershipRepo;
    @Mock
    private CurrentUserService currentUser;
    @Mock
    private MembershipIdResolver roleIds;

    @InjectMocks
    private ArchiveProjectUseCase useCase;

    @Test
    void execute_archivesProjectWhenCurrentUserIsOwner() {
        Long userId = 10L;
        Long projectId = 99L;

        when(currentUser.getRequiredUserId()).thenReturn(userId);
        when(roleIds.ownerId()).thenReturn(1L);
        when(membershipRepo.find(userId, projectId)).thenReturn(Optional.of(new Membership(userId, projectId, 1L)));
        when(projectRepo.findById(projectId)).thenReturn(Optional.of(new Project()));

        useCase.execute(projectId);

        verify(projectRepo).archive(projectId);
    }

    @Test
    void execute_throwsForbiddenWhenCurrentUserIsNotOwner() {
        Long userId = 10L;
        Long projectId = 99L;

        when(currentUser.getRequiredUserId()).thenReturn(userId);
        when(roleIds.ownerId()).thenReturn(1L);
        when(membershipRepo.find(userId, projectId)).thenReturn(Optional.of(new Membership(userId, projectId, 2L)));

        assertThrows(ForbiddenException.class, () -> useCase.execute(projectId));
        verify(projectRepo, never()).archive(projectId);
    }
}

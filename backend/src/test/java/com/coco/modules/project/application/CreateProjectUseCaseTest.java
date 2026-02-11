package com.coco.modules.project.application;

import com.coco.modules.project.api.dto.CreateProjectCommand;
import com.coco.modules.project.application.members.MembershipIdResolver;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.application.port.ProjectRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.modules.project.domain.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProjectUseCaseTest {

    @Mock
    private ProjectRepositoryPort projectRepo;
    @Mock
    private MembershipRepositoryPort membershipRepo;
    @Mock
    private ProjectAuthorizationService authz;
    @Mock
    private MembershipIdResolver roleIdResolver;

    @InjectMocks
    private CreateProjectUseCase useCase;

    @Test
    void execute_createsProjectAndOwnerMembership() {
        when(authz.currentUserId()).thenReturn(7L);
        when(roleIdResolver.ownerId()).thenReturn(1L);
        when(projectRepo.save(any(Project.class))).thenAnswer(invocation -> {
            Project saved = invocation.getArgument(0, Project.class);
            saved.setId(100L);
            return saved;
        });
        when(membershipRepo.save(any(Membership.class))).thenAnswer(invocation -> invocation.getArgument(0, Membership.class));

        Project result = useCase.execute(new CreateProjectCommand("COCO", "desc", "logo.png"));

        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepo).save(projectCaptor.capture());
        Project persistedProject = projectCaptor.getValue();
        assertEquals("COCO", persistedProject.getName());
        assertEquals("desc", persistedProject.getDescription());
        assertEquals("logo.png", persistedProject.getLogoUrl());
        assertEquals("ACTIVE", persistedProject.getStatus());

        ArgumentCaptor<Membership> membershipCaptor = ArgumentCaptor.forClass(Membership.class);
        verify(membershipRepo).save(membershipCaptor.capture());
        Membership persistedMembership = membershipCaptor.getValue();
        assertEquals(7L, persistedMembership.getUserId());
        assertEquals(100L, persistedMembership.getProjectId());
        assertEquals(1L, persistedMembership.getRoleId());

        assertEquals(100L, result.getId());
        var ordered = inOrder(projectRepo, membershipRepo);
        ordered.verify(projectRepo).save(any(Project.class));
        ordered.verify(membershipRepo).save(any(Membership.class));
    }
}

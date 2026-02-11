package com.coco.modules.project.application.members;

import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.modules.project.domain.ProjectPermission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMembersUseCase {

    private final MembershipRepositoryPort membershipRepo;
    private final ProjectAuthorizationService authz;

    public ListMembersUseCase(MembershipRepositoryPort membershipRepo, ProjectAuthorizationService authz) {
        this.membershipRepo = membershipRepo;
        this.authz = authz;
    }

    public List<Membership> execute(Long projectId) {
        authz.requirePermission(projectId, ProjectPermission.READ);
        return membershipRepo.findAllByProject(projectId);
    }
}

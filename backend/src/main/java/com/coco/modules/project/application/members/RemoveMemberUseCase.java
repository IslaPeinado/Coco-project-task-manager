package com.coco.modules.project.application.members;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.ProjectPermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveMemberUseCase {

    private final MembershipRepositoryPort membershipRepo;
    private final ProjectAuthorizationService authz;

    public RemoveMemberUseCase(MembershipRepositoryPort membershipRepo, ProjectAuthorizationService authz) {
        this.membershipRepo = membershipRepo;
        this.authz = authz;
    }

    @Transactional
    public void execute(Long projectId, Long userId) {
        authz.requirePermission(projectId, ProjectPermission.MANAGE);
        if (!membershipRepo.exists(userId, projectId)) {
            throw new NotFoundException("Member not found");
        }
        membershipRepo.remove(userId, projectId);
    }
}

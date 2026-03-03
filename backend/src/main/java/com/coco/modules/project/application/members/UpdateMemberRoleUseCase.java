package com.coco.modules.project.application.members;

import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.modules.project.domain.ProjectPermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMemberRoleUseCase {

    private final MembershipRepositoryPort membershipRepo;
    private final ProjectAuthorizationService authz;

    public UpdateMemberRoleUseCase(MembershipRepositoryPort membershipRepo, ProjectAuthorizationService authz) {
        this.membershipRepo = membershipRepo;
        this.authz = authz;
    }

    @Transactional
    public Membership execute(Long projectId, Long userId, Long newRoleId) {
        authz.requirePermission(projectId, ProjectPermission.MANAGE);
        var membership = membershipRepo.find(userId, projectId)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        membership.setRoleId(newRoleId);
        return membershipRepo.save(membership);
    }
}

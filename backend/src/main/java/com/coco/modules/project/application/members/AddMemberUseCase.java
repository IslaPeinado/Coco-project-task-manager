package com.coco.modules.project.application.members;

import com.coco.common.util.ConflictException;
import com.coco.modules.project.application.ProjectAuthorizationService;
import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.Membership;
import com.coco.modules.project.domain.ProjectPermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddMemberUseCase {

    private final MembershipRepositoryPort membershipRepo;
    private final ProjectAuthorizationService authz;

    public AddMemberUseCase(MembershipRepositoryPort membershipRepo, ProjectAuthorizationService authz) {
        this.membershipRepo = membershipRepo;
        this.authz = authz;
    }

    @Transactional
    public Membership execute(Long projectId, Long userId, Long roleId) {
        authz.requirePermission(projectId, ProjectPermission.MANAGE);
        if (membershipRepo.exists(userId, projectId)) {
            throw new ConflictException("User already is member of project");
        }
        return membershipRepo.save(new Membership(userId, projectId, roleId));
    }
}

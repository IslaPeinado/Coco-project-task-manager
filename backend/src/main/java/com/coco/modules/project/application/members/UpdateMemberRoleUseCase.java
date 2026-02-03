package com.coco.modules.project.application.members;

import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.Membership;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMemberRoleUseCase {

    private final MembershipRepositoryPort membershipRepo;

    public UpdateMemberRoleUseCase(MembershipRepositoryPort membershipRepo) {
        this.membershipRepo = membershipRepo;
    }

    @Transactional
    public Membership execute(Long projectId, Long userId, Long newRoleId) {
        var membership = membershipRepo.find(userId, projectId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        membership.setRoleId(newRoleId);
        return membershipRepo.save(membership);
    }
}

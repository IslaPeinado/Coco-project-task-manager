package com.coco.modules.project.application.members;

import com.coco.modules.project.application.port.MembershipRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveMemberUseCase {

    private final MembershipRepositoryPort membershipRepo;

    public RemoveMemberUseCase(MembershipRepositoryPort membershipRepo) {
        this.membershipRepo = membershipRepo;
    }

    @Transactional
    public void execute(Long projectId, Long userId) {
        if (!membershipRepo.exists(userId, projectId)) {
            throw new IllegalArgumentException("Member not found");
        }
        membershipRepo.remove(userId, projectId);
    }
}

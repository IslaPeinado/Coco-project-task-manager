package com.coco.modules.project.application.members;

import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.Membership;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddMemberUseCase {

    private final MembershipRepositoryPort membershipRepo;

    public AddMemberUseCase(MembershipRepositoryPort membershipRepo) {
        this.membershipRepo = membershipRepo;
    }

    @Transactional
    public Membership execute(Long projectId, Long userId, Long roleId) {
        if (membershipRepo.exists(userId, projectId)) {
            // Puedes cambiar esto a una excepción custom cuando tengas tu paquete common/errors.
            throw new IllegalStateException("User already is member of project");
        }
        return membershipRepo.save(new Membership(userId, projectId, roleId));
    }
}

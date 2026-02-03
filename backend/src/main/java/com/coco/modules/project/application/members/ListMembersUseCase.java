package com.coco.modules.project.application.members;

import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.Membership;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMembersUseCase {

    private final MembershipRepositoryPort membershipRepo;

    public ListMembersUseCase(MembershipRepositoryPort membershipRepo) {
        this.membershipRepo = membershipRepo;
    }

    public List<Membership> execute(Long projectId) {
        return membershipRepo.findAllByProject(projectId);
    }
}

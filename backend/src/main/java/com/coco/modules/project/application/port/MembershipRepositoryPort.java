package com.coco.modules.project.application.port;

import com.coco.modules.project.domain.Membership;

import java.util.List;
import java.util.Optional;

public interface MembershipRepositoryPort {

    boolean exists(Long userId, Long projectId);

    Optional<Membership> find(Long userId, Long projectId);

    List<Membership> findAllByProject(Long projectId);

    Membership save(Membership membership);

    void remove(Long userId, Long projectId);
}

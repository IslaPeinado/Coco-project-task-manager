package com.coco.modules.project.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipJpaRepository extends JpaRepository<MembershipEntity, MembershipId> {

    boolean existsByIdUserIdAndIdProjectId(Long userId, Long projectId);

    Optional<MembershipEntity> findByIdUserIdAndIdProjectId(Long userId, Long projectId);

    List<MembershipEntity> findAllByIdProjectId(Long projectId);

    void deleteByIdUserIdAndIdProjectId(Long userId, Long projectId);
}

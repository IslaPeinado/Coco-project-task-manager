package com.coco.modules.project.infra.persistence;

import com.coco.modules.project.application.port.MembershipRepositoryPort;
import com.coco.modules.project.domain.Membership;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class MembershipRepositoryAdapter implements MembershipRepositoryPort {

    private final MembershipJpaRepository repo;

    public MembershipRepositoryAdapter(MembershipJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean exists(Long userId, Long projectId) {
        return repo.existsByIdUserIdAndIdProjectId(userId, projectId);
    }

    @Override
    public Optional<Membership> find(Long userId, Long projectId) {
        return repo.findByIdUserIdAndIdProjectId(userId, projectId).map(MembershipEntity::toDomain);
    }

    @Override
    public List<Membership> findAllByProject(Long projectId) {
        return repo.findAllByIdProjectId(projectId)
                .stream()
                .map(MembershipEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Membership save(Membership membership) {
        var entity = MembershipEntity.fromDomain(membership);
        return repo.save(entity).toDomain();
    }

    @Override
    @Transactional
    public void remove(Long userId, Long projectId) {
        repo.deleteByIdUserIdAndIdProjectId(userId, projectId);
    }
}

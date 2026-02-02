package com.coco.modules.project.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, Long> {

    // Listar proyetos activos
    @Query("select p from ProjectEntity p where p.status <> 'ARCHIVED' order by p.updatedAt desc")
    List<ProjectEntity> findAllActive();

    // Listar TODOS los proyectos
    @Query("select p from ProjectEntity p order by p.updatedAt desc")
    List<ProjectEntity> findAllOrdered();

    @Modifying
    @Query("""
    update ProjectEntity p
    set p.status = 'ARCHIVED',
        p.archivedAt = :archivedAt
    where p.id = :id
    """)
    int archiveById(Long id, OffsetDateTime archivedAt);

}
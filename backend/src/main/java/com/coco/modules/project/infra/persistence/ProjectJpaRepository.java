package com.coco.modules.project.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, Long> {

    @Query(value = """
        SELECT p.*
        FROM projects p
        JOIN cocouser_project_role m ON m.project_id = p.id
        WHERE m.user_id = :userId
          AND p.archived_at IS NULL
          AND p.status = 'ACTIVE'
        ORDER BY p.created_at DESC
        """, nativeQuery = true)
    List<ProjectEntity> findAccessibleActiveByUserId(@Param("userId") Long userId);

    @Query(value = """
        SELECT p.*
        FROM projects p
        JOIN cocouser_project_role m ON m.project_id = p.id
        WHERE m.user_id = :userId
        ORDER BY p.created_at DESC
        """, nativeQuery = true)
    List<ProjectEntity> findAccessibleAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("""
    update ProjectEntity p
    set p.status = 'ARCHIVED',
        p.archivedAt = :archivedAt
    where p.id = :id
    """)
    int archiveById(Long id, OffsetDateTime archivedAt);

}
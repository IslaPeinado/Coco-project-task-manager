package com.coco.modules.task.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskStatusJpaRepository extends JpaRepository<TaskStatusEntity, Long> {

}

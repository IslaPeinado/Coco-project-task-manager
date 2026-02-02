package com.coco.modules.project.application;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArchiveProjectUseCase {

    private final ProjectRepositoryPort repo;

    public ArchiveProjectUseCase(ProjectRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    public void execute(Long id) {
        repo.archive(id);
    }
}

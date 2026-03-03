package com.coco.modules.project.application;

import com.coco.modules.project.application.port.ProjectRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class PurgeArchivedProjectsJob {
    private static final Logger log = LoggerFactory.getLogger(PurgeArchivedProjectsJob.class);

    private final ProjectRepositoryPort projectRepo;
    private final int retentionDays;
    private final boolean enabled;

    public PurgeArchivedProjectsJob(
            ProjectRepositoryPort projectRepo,
            @Value("${coco.project.purge.retention-days:10}") int retentionDays,
            @Value("${coco.project.purge.enabled:true}") boolean enabled
    ) {
        this.projectRepo = projectRepo;
        this.retentionDays = retentionDays;
        this.enabled = enabled;
    }

    @Scheduled(cron = "${coco.project.purge.cron:0 0 3 * * *}", zone = "UTC")
    @Transactional
    public void run() {
        if (!enabled) {
            return;
        }

        OffsetDateTime cutoff = OffsetDateTime.now(ZoneOffset.UTC).minusDays(retentionDays);
        int deleted = projectRepo.purgeArchivedBefore(cutoff);
        if (deleted > 0) {
            log.info("Purged {} archived projects older than {} days (cutoff={})", deleted, retentionDays, cutoff);
        }
    }
}

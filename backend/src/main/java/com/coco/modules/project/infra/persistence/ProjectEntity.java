package com.coco.modules.project.infra.persistence;

import com.coco.modules.project.domain.Project;
import com.coco.modules.project.domain.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "logo_url", length = Integer.MAX_VALUE)
    private String logoUrl;

    @NotNull
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ProjectStatus status;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @ColumnDefault("now()")
    @Column(name = "archived_at")
    private OffsetDateTime archivedAt;

    public static ProjectEntity fromDomain(Project project){
        ProjectEntity entity = new ProjectEntity();

        entity.id = project.getId();
        entity.name = project.getName();
        entity.description = project.getDescription();
        entity.logoUrl = project.getLogoUrl();
        entity.status = ProjectStatus.valueOf(project.getStatus());
        entity.createdAt = project.getCreatedAt();
        entity.updatedAt = project.getUpdatedAt();
        entity.archivedAt = project.getArchivedAt();

        return entity;
    }

    public Project toDomain(){
        Project project = new Project();

        project.setId(this.id);
        project.setName(this.name);
        project.setDescription(this.description);
        project.setLogoUrl(this.logoUrl);
        project.setStatus(String.valueOf(this.status));
        project.setCreatedAt(this.createdAt);
        project.setUpdatedAt(this.updatedAt);
        project.setArchivedAt(this.archivedAt);

        return project;
    }

}

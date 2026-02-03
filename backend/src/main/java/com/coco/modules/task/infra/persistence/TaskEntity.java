package com.coco.modules.task.infra.persistence;

import com.coco.modules.task.domain.Task;
import com.coco.modules.user.infra.persistence.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "status", nullable = false)
    private TaskStatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "assigned_to")
    private UserEntity assignedTo;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public static TaskEntity fromDomain(Task task) {
        TaskEntity entity = new TaskEntity();

        entity.id = task.getId();
        entity.title = task.getTitle();
        entity.description = task.getDescription();
        entity.status = task.getStatus();
        entity.assignedTo = task.getAssignedTo();
        entity.dueDate = task.getDueDate();
        entity.createdAt = task.getCreatedAt();
        entity.updatedAt = task.getUpdatedAt();

        return entity;
    }

    public Task toDomain() {
        Task task = new Task();

        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setStatus(this.status);
        task.setAssignedTo(this.assignedTo);
        task.setDueDate(this.dueDate);
        task.setCreatedAt(this.createdAt);
        task.setUpdatedAt(this.updatedAt);

        return task;
    }

}

package com.coco.modules.task.infra.persistence;

import com.coco.modules.task.domain.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "task_status")
public class TaskStatusEntity {
    @Id
    @Size(max = 50)
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Size(max = 100)
    @NotNull
    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Size(max = 7)
    @NotNull
    @Column(name = "color_hex", nullable = false, length = 7)
    private String colorHex;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Short sortOrder;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_terminal", nullable = false)
    private Boolean isTerminal;

    @OneToMany(mappedBy = "status")
    private Set<TaskEntity> taskEntities = new LinkedHashSet<>();

    public static TaskStatusEntity fromDomain(TaskStatus taskStatus) {
        TaskStatusEntity entity = new TaskStatusEntity();

        entity.status = taskStatus.getStatus();
        entity.displayName = taskStatus.getDisplayName();
        entity.colorHex = taskStatus.getColorHex();
        entity.sortOrder = taskStatus.getSortOrder();
        entity.isTerminal = taskStatus.getIsTerminal();

        return entity;
    }

    public TaskStatus toDomain() {
        TaskStatus taskStatus = new TaskStatus();

        taskStatus.setStatus(this.status);
        taskStatus.setDisplayName(this.displayName);
        taskStatus.setColorHex(this.colorHex);
        taskStatus.setSortOrder(this.sortOrder);
        taskStatus.setIsTerminal(this.isTerminal);

        return taskStatus;
    }

}

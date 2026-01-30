package com.coco.modules.task.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "task_status")
public class TaskStatus {
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


}
package com.coco.modules.project.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


/**
 * PK compuesta para cocouser_project_role: (user_id, project_id)
 */
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class MembershipId implements Serializable {


    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;


    protected MembershipId() {
    }

    public MembershipId(Long userId, Long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MembershipId that)) return false;
        return Objects.equals(userId, that.userId)
                && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, projectId);
    }

}
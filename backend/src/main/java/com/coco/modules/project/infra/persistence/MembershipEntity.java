package com.coco.modules.project.infra.persistence;

import com.coco.modules.project.domain.Membership;
import com.coco.modules.user.infra.persistence.RoleEntity;
import com.coco.modules.user.infra.persistence.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "cocouser_project_role")
public class MembershipEntity {
    @EmbeddedId
    private MembershipId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @MapsId("projectId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    public static MembershipEntity fromDomain(Membership membership) {
        MembershipEntity entity = new MembershipEntity();

        entity.id = membership.getId();
        entity.user = membership.getUser();
        entity.project = membership.getProject();
        entity.role = membership.getRole();

        return entity;
    }

    public Membership toDomain() {
        Membership membership = new Membership();

        membership.setId(this.id);
        membership.setUser(this.user);
        membership.setProject(this.project);
        membership.setRole(this.role);

        return membership;
    }

}

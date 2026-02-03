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

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    protected MembershipEntity() {
    }

    public MembershipEntity(MembershipId id, Long roleId) {
        this.id = id;
        this.roleId = roleId;
    }

    // --- mapping domain ---

    public Membership toDomain() {
        return new Membership(id.getUserId(), id.getProjectId(), roleId);
    }

    public static MembershipEntity fromDomain(Membership m) {
        return new MembershipEntity(new MembershipId(m.getUserId(), m.getProjectId()), m.getRoleId());
    }

}

package com.coco.modules.user.infra.persistence;

import com.coco.modules.project.infra.persistence.MembershipEntity;
import com.coco.modules.user.domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<MembershipEntity> membershipEntities = new LinkedHashSet<>();

    public static RoleEntity fromDomain(Role role) {
        RoleEntity entity = new RoleEntity();

        entity.id = role.getId();
        entity.roleName = role.getRoleName();

        return entity;
    }

    public Role toDomain() {
        Role role = new Role();

        role.setId(this.id);
        role.setRoleName(this.roleName);

        return role;
    }

}

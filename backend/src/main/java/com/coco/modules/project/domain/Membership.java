package com.coco.modules.project.domain;

import com.coco.modules.project.infra.persistence.MembershipId;
import com.coco.modules.project.infra.persistence.ProjectEntity;
import com.coco.modules.user.infra.persistence.RoleEntity;
import com.coco.modules.user.infra.persistence.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Membership {

    private MembershipId id;
    private UserEntity user;
    private ProjectEntity project;
    private RoleEntity role;


}

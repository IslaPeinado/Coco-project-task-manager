package com.coco.modules.project.api.mapper;

import com.coco.modules.project.api.dto.MemberResponse;
import com.coco.modules.project.domain.Membership;

public final class MemberApiMapper {

    private MemberApiMapper() {}

    public static MemberResponse toResponse(Membership m) {
        return new MemberResponse(m.getUserId(), m.getProjectId(), m.getRoleId());
    }
}

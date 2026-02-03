package com.coco.modules.project.api;

import com.coco.modules.project.api.dto.AddMemberRequest;
import com.coco.modules.project.api.dto.MemberResponse;
import com.coco.modules.project.api.dto.UpdateMemberRoleRequest;
import com.coco.modules.project.api.mapper.MemberApiMapper;
import com.coco.modules.project.application.members.AddMemberUseCase;
import com.coco.modules.project.application.members.ListMembersUseCase;
import com.coco.modules.project.application.members.RemoveMemberUseCase;
import com.coco.modules.project.application.members.UpdateMemberRoleUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMembersController {

    private final AddMemberUseCase addMember;
    private final RemoveMemberUseCase removeMember;
    private final UpdateMemberRoleUseCase updateRole;
    private final ListMembersUseCase listMembers;

    public ProjectMembersController(
            AddMemberUseCase addMember,
            RemoveMemberUseCase removeMember,
            UpdateMemberRoleUseCase updateRole,
            ListMembersUseCase listMembers
    ) {
        this.addMember = addMember;
        this.removeMember = removeMember;
        this.updateRole = updateRole;
        this.listMembers = listMembers;
    }

    @GetMapping
    public List<MemberResponse> list(@PathVariable Long projectId) {
        return listMembers.execute(projectId)
                .stream()
                .map(MemberApiMapper::toResponse)
                .toList();
    }

    @PostMapping
    public MemberResponse add(@PathVariable Long projectId, @Valid @RequestBody AddMemberRequest req) {
        var m = addMember.execute(projectId, req.userId(), req.roleId());
        return MemberApiMapper.toResponse(m);
    }

    @PutMapping("/{userId}")
    public MemberResponse updateRole(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @Valid @RequestBody UpdateMemberRoleRequest req
    ) {
        var m = updateRole.execute(projectId, userId, req.roleId());
        return MemberApiMapper.toResponse(m);
    }

    @DeleteMapping("/{userId}")
    public void remove(@PathVariable Long projectId, @PathVariable Long userId) {
        removeMember.execute(projectId, userId);
    }
}

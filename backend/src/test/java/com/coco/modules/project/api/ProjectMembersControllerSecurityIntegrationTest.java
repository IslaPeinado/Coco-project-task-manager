package com.coco.modules.project.api;

import com.coco.common.error.GlobalExceptionHandler;
import com.coco.common.util.ConflictException;
import com.coco.common.util.ForbiddenException;
import com.coco.common.util.NotFoundException;
import com.coco.modules.project.application.members.AddMemberUseCase;
import com.coco.modules.project.application.members.ListMembersUseCase;
import com.coco.modules.project.application.members.RemoveMemberUseCase;
import com.coco.modules.project.application.members.UpdateMemberRoleUseCase;
import com.coco.modules.project.domain.Membership;
import com.coco.security.RestAccessDeniedHandler;
import com.coco.security.RestAuthenticationEntryPoint;
import com.coco.security.SecurityConfig;
import com.coco.security.jwt.JwtAuthFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectMembersController.class)
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        RestAuthenticationEntryPoint.class,
        RestAccessDeniedHandler.class
})
class ProjectMembersControllerSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddMemberUseCase addMember;
    @MockitoBean
    private RemoveMemberUseCase removeMember;
    @MockitoBean
    private UpdateMemberRoleUseCase updateRole;
    @MockitoBean
    private ListMembersUseCase listMembers;
    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setupJwtFilterPassThrough() throws Exception {
        doAnswer(invocation -> {
            ServletRequest request = invocation.getArgument(0, ServletRequest.class);
            ServletResponse response = invocation.getArgument(1, ServletResponse.class);
            FilterChain chain = invocation.getArgument(2, FilterChain.class);
            chain.doFilter(request, response);
            return null;
        }).when(jwtAuthFilter).doFilter(any(), any(), any());
    }

    @Test
    void list_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/projects/1/members"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void list_withAuthentication_returnsOk() throws Exception {
        when(listMembers.execute(1L)).thenReturn(List.of(new Membership(10L, 1L, 2L)));

        mockMvc.perform(get("/api/projects/1/members"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"userId":10,"projectId":1,"roleId":2}]
                        """));

        verify(listMembers).execute(1L);
    }

    @Test
    void add_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/projects/1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userId":10,"roleId":2}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void add_withAuthentication_returnsOk() throws Exception {
        when(addMember.execute(1L, 10L, 2L)).thenReturn(new Membership(10L, 1L, 2L));

        mockMvc.perform(post("/api/projects/1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userId":10,"roleId":2}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.roleId").value(2));

        verify(addMember).execute(1L, 10L, 2L);
    }

    @Test
    @WithMockUser(username = "1")
    void add_withInvalidPayload_returnsValidationErrorEnvelope() throws Exception {
        mockMvc.perform(post("/api/projects/1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userId":10}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.path").value("/api/projects/1/members"))
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    @WithMockUser(username = "1")
    void add_whenMemberAlreadyExists_returnsConflictEnvelope() throws Exception {
        doThrow(new ConflictException("User already is member of project"))
                .when(addMember).execute(1L, 10L, 2L);

        mockMvc.perform(post("/api/projects/1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"userId":10,"roleId":2}
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("CONFLICT"))
                .andExpect(jsonPath("$.path").value("/api/projects/1/members"));
    }

    @Test
    void updateRole_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(put("/api/projects/1/members/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"roleId":3}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void updateRole_withAuthentication_returnsOk() throws Exception {
        when(updateRole.execute(1L, 10L, 3L)).thenReturn(new Membership(10L, 1L, 3L));

        mockMvc.perform(put("/api/projects/1/members/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"roleId":3}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.roleId").value(3));
    }

    @Test
    @WithMockUser(username = "1")
    void updateRole_whenMemberNotFound_returnsNotFoundEnvelope() throws Exception {
        doThrow(new NotFoundException("Member not found"))
                .when(updateRole).execute(1L, 10L, 3L);

        mockMvc.perform(put("/api/projects/1/members/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"roleId":3}
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.path").value("/api/projects/1/members/10"));
    }

    @Test
    void remove_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/projects/1/members/10"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void remove_withAuthentication_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/projects/1/members/10"))
                .andExpect(status().isOk());

        verify(removeMember).execute(1L, 10L);
    }

    @Test
    @WithMockUser(username = "1")
    void remove_withoutManagePermission_returnsForbiddenEnvelope() throws Exception {
        doThrow(new ForbiddenException("No tienes los suficientes pribilegios"))
                .when(removeMember).execute(1L, 10L);

        mockMvc.perform(delete("/api/projects/1/members/10"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("FORBIDDEN"))
                .andExpect(jsonPath("$.path").value("/api/projects/1/members/10"));
    }
}

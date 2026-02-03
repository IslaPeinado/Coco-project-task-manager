package com.coco.modules.project.api;

import com.coco.common.error.GlobalExceptionHandler;
import com.coco.modules.project.application.ArchiveProjectUseCase;
import com.coco.modules.project.application.CreateProjectUseCase;
import com.coco.modules.project.application.GetProjectUseCase;
import com.coco.modules.project.application.ListProjectsUseCase;
import com.coco.modules.project.application.UpdateProjectUseCase;
import com.coco.modules.project.domain.Project;
import com.coco.security.SecurityConfig;
import com.coco.security.jwt.JwtAuthFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ProjectControllerSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListProjectsUseCase listProjects;
    @MockitoBean
    private GetProjectUseCase getProject;
    @MockitoBean
    private CreateProjectUseCase create;
    @MockitoBean
    private UpdateProjectUseCase update;
    @MockitoBean
    private ArchiveProjectUseCase archive;
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
    void list_withoutAuthentication_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "1")
    void list_withAuthentication_returnsOk() throws Exception {
        when(listProjects.execute(false)).thenReturn(List.of());

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(listProjects).execute(false);
    }

    @Test
    void create_withoutAuthentication_returnsForbidden() throws Exception {
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"COCO","description":"d","logoUrl":"l"}
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "1")
    void create_withAuthentication_returnsOk() throws Exception {
        Project project = new Project();
        project.setId(10L);
        project.setName("COCO");
        project.setDescription("d");
        project.setLogoUrl("l");
        project.setStatus("ACTIVE");
        project.setCreatedAt(OffsetDateTime.now());
        project.setUpdatedAt(OffsetDateTime.now());

        when(create.execute(any())).thenReturn(project);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"COCO","description":"d","logoUrl":"l"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("COCO"));
    }
}

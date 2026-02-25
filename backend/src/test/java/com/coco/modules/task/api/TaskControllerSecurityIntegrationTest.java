package com.coco.modules.task.api;

import com.coco.common.error.GlobalExceptionHandler;
import com.coco.common.util.ForbiddenException;
import com.coco.modules.task.application.CreateTaskUseCase;
import com.coco.modules.task.application.DeleteTaskUseCase;
import com.coco.modules.task.application.GetTaskUseCase;
import com.coco.modules.task.application.ListTasksUseCase;
import com.coco.modules.task.application.ChangeTaskStatusUseCase;
import com.coco.modules.task.application.UpdateTaskUseCase;
import com.coco.modules.task.domain.Task;
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

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        RestAuthenticationEntryPoint.class,
        RestAccessDeniedHandler.class
})
class TaskControllerSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListTasksUseCase listTasks;
    @MockitoBean
    private GetTaskUseCase getTask;
    @MockitoBean
    private CreateTaskUseCase createTask;
    @MockitoBean
    private UpdateTaskUseCase updateTask;
    @MockitoBean
    private ChangeTaskStatusUseCase changeTaskStatus;
    @MockitoBean
    private DeleteTaskUseCase deleteTask;
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
        mockMvc.perform(get("/api/projects/1/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void list_withAuthentication_returnsOk() throws Exception {
        when(listTasks.execute(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/projects/1/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(listTasks).execute(1L);
    }

    @Test
    @WithMockUser(username = "1")
    void list_withoutPermissions_returnsForbidden() throws Exception {
        when(listTasks.execute(1L)).thenThrow(new ForbiddenException("No tienes los suficientes pribilegios"));

        mockMvc.perform(get("/api/projects/1/tasks"))
                .andExpect(status().isForbidden());
    }

    @Test
    void create_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/projects/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Task 1","description":"d","status":"TODO"}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void changeStatus_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(put("/api/projects/1/tasks/10/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"status":"IN_PROGRESS"}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void create_withAuthentication_returnsCreated() throws Exception {
        Task task = new Task();
        task.setId(10L);
        task.setProjectId(1L);
        task.setTitle("Task 1");
        task.setDescription("d");
        task.setStatus("TODO");
        task.setCreatedAt(OffsetDateTime.now());
        task.setUpdatedAt(OffsetDateTime.now());

        when(createTask.execute(any(), any())).thenReturn(task);

        mockMvc.perform(post("/api/projects/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Task 1","description":"d","status":"TODO"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    @WithMockUser(username = "1")
    void changeStatus_withAuthentication_returnsUpdatedTaskResponse() throws Exception {
        Task task = new Task();
        task.setId(10L);
        task.setProjectId(1L);
        task.setTitle("Task 1");
        task.setDescription("d");
        task.setStatus("IN_PROGRESS");
        task.setCreatedAt(OffsetDateTime.now());
        task.setUpdatedAt(OffsetDateTime.now());

        when(changeTaskStatus.execute(1L, 10L, "IN_PROGRESS")).thenReturn(task);

        mockMvc.perform(put("/api/projects/1/tasks/10/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"status":"IN_PROGRESS"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        verify(changeTaskStatus).execute(eq(1L), eq(10L), eq("IN_PROGRESS"));
    }
}

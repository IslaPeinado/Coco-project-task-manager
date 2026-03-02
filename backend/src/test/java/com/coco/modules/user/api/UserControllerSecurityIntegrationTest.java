package com.coco.modules.user.api;

import com.coco.common.error.GlobalExceptionHandler;
import com.coco.modules.user.api.mapper.UserApiMapper;
import com.coco.modules.user.application.user.ChangePasswordUseCase;
import com.coco.modules.user.application.user.GetMeUseCase;
import com.coco.modules.user.application.user.UpdateUserUseCase;
import com.coco.modules.user.domain.User;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        RestAuthenticationEntryPoint.class,
        RestAccessDeniedHandler.class,
        UserApiMapper.class
})
class UserControllerSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetMeUseCase getMeUseCase;
    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;
    @MockitoBean
    private ChangePasswordUseCase changePasswordUseCase;
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
    void getMe_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void updateMe_withAuthentication_returnsUpdatedUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("ana@coco.dev");
        user.setFirstName("Ana");
        user.setLastName("User");
        user.setImageUrl("https://img.dev/me.png");
        user.setCreatedAt(OffsetDateTime.parse("2026-03-02T10:15:30Z"));
        user.setUpdatedAt(OffsetDateTime.parse("2026-03-02T11:15:30Z"));

        when(updateUserUseCase.execute(any())).thenReturn(user);

        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"ana@coco.dev","firstName":"Ana","lastName":"User","imageUrl":"https://img.dev/me.png"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("ana@coco.dev"))
                .andExpect(jsonPath("$.firstName").value("Ana"));

        verify(updateUserUseCase).execute(any());
    }

    @Test
    @WithMockUser(username = "1")
    void updateMe_withInvalidBody_returnsValidationErrorEnvelope() throws Exception {
        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"","firstName":"","lastName":"User"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.path").value("/api/users/me"))
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void changePassword_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/users/me/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"currentPassword":"old","newPassword":"new"}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "1")
    void changePassword_withAuthentication_returnsNoContent() throws Exception {
        mockMvc.perform(post("/api/users/me/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"currentPassword":"old","newPassword":"new"}
                                """))
                .andExpect(status().isNoContent());

        verify(changePasswordUseCase).execute(any());
    }

    @Test
    @WithMockUser(username = "1")
    void changePassword_withWrongCurrentPassword_returnsUnauthorizedEnvelope() throws Exception {
        doThrow(new com.coco.common.util.UnauthorizedException("Current password is incorrect"))
                .when(changePasswordUseCase).execute(any());

        mockMvc.perform(post("/api/users/me/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"currentPassword":"old","newPassword":"new"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value("Current password is incorrect"))
                .andExpect(jsonPath("$.path").value("/api/users/me/change-password"));
    }
}

package com.coco.modules.user.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank
        @Size(max = 255)
        String currentPassword,

        @NotBlank
        @Size(max = 255)
        String newPassword
) {
}

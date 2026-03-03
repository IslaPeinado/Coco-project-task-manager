package com.coco.modules.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Email
        @NotBlank
        @Size(max = 255)
        String email,

        @NotBlank
        @Size(max = 255)
        String firstName,

        @Size(max = 255)
        String lastName,

        String imageUrl
) {
}

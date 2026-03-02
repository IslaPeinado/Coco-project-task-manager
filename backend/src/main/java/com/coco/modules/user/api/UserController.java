package com.coco.modules.user.api;

import com.coco.modules.user.api.dto.ChangePasswordRequest;
import com.coco.modules.user.api.dto.UserResponse;
import com.coco.modules.user.api.dto.UserUpdateRequest;
import com.coco.modules.user.api.mapper.UserApiMapper;
import com.coco.modules.user.application.user.ChangePasswordUseCase;
import com.coco.modules.user.application.user.GetMeUseCase;
import com.coco.modules.user.application.user.UpdateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final GetMeUseCase getMeUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final UserApiMapper userApiMapper;

    @GetMapping("/me")
    public UserResponse me() {
        return userApiMapper.toResponse(getMeUseCase.execute());
    }

    @PutMapping("/me")
    public UserResponse updateMe(@Valid @RequestBody UserUpdateRequest request) {
        return userApiMapper.toResponse(updateUserUseCase.execute(request));
    }

    @PostMapping("/me/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        changePasswordUseCase.execute(request);
    }
}

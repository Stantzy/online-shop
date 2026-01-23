package io.github.onlineshop.users.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UserPasswordChangeRequest {
    @NotBlank
    String oldPassword;

    @NotBlank
    String newPassword;
}

package io.github.onlineshop.users.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordChangeRequest(
    @NotBlank
    String oldPassword,
    @NotBlank
    String newPassword
) {

}

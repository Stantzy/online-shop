package io.github.onlineshop.users.api.dto.request;

public record UserPasswordChangeRequest(
        String oldPassword,
        String newPassword
) {
}

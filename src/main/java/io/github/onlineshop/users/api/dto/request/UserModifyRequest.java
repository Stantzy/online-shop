package io.github.onlineshop.users.api.dto.request;

import io.github.onlineshop.security.UserRole;

public record UserModifyRequest(
        String username,
        String email,
        UserRole role
) {

}

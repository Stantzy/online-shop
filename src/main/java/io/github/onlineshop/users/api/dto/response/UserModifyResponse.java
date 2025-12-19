package io.github.onlineshop.users.api.dto.response;

import io.github.onlineshop.security.UserRole;

public record UserModifyResponse(
    String username,
    String email,
    UserRole role
) {

}

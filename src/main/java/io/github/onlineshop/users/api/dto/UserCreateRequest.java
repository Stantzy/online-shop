package io.github.onlineshop.users.api.dto;

public record UserCreateRequest(
        String username,
        String email,
        String password
) {
}

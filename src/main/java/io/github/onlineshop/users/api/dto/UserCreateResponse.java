package io.github.onlineshop.users.api.dto;

public record UserCreateResponse(
    String username,
    String email
) {
}

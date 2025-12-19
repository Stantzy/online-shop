package io.github.onlineshop.users.api.dto.request;

public record UserCreateRequest(
    String username,
    String email,
    String password
) {

}

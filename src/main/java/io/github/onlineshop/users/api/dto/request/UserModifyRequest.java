package io.github.onlineshop.users.api.dto.request;

public record UserModifyRequest(
    String username,
    String email
) {

}

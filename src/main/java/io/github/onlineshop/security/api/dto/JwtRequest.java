package io.github.onlineshop.security.api.dto;

public record JwtRequest(
    String username,
    String password
) {

}

package io.github.onlineshop.security.dto;

public record JwtRequest(
    String username,
    String password
) {

}

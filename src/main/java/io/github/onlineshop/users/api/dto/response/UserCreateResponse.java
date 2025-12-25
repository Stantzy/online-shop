package io.github.onlineshop.users.api.dto.response;

import io.github.onlineshop.security.UserRole;

import java.time.LocalDate;

public record UserCreateResponse(
    Long id,
    String username,
    String email,
    LocalDate registrationDate,
    UserRole role
) {

}

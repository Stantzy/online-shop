package io.github.onlineshop.users.api.dto.response;

import java.time.LocalDate;

public record UserCreateResponse(
    String username,
    String email,
    LocalDate registrationDate
) {
}

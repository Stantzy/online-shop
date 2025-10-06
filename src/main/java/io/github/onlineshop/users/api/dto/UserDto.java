package io.github.onlineshop.users.api.dto;

import java.time.LocalDate;

public record UserDto(
        String username,
        String email,
        LocalDate registrationDate
) {
}

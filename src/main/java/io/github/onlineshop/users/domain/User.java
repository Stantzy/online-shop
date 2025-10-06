package io.github.onlineshop.users.domain;

import java.time.LocalDate;

public record User(
        String username,
        String email,
        String passwordHash,
        LocalDate registrationDate
) {
}
package io.github.onlineshop.users.domain;

import io.github.onlineshop.orders.api.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

public record User(
        String username,
        String email,
        String passwordHash,
        LocalDate registrationDate,
        List<OrderDto> orders
) {
}
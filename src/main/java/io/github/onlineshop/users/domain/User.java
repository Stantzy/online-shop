package io.github.onlineshop.users.domain;

import io.github.onlineshop.orders.domain.Order;
import io.github.onlineshop.security.UserRole;

import java.time.LocalDate;
import java.util.List;

public record User(
    Long id,
    String username,
    String email,
    String passwordHash,
    LocalDate registrationDate,
    List<Order> orders,
    UserRole role
) {
}
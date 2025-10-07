package io.github.onlineshop.users.api.dto;

import io.github.onlineshop.orders.api.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        String username,
        String email,
        LocalDate registrationDate,
        List<OrderDto> orders
) {
}

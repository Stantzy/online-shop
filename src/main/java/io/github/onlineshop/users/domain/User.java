package io.github.onlineshop.users.domain;

import io.github.onlineshop.orders.domain.Order;
import io.github.onlineshop.security.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private LocalDate registrationDate;
    private List<Order> orders;
    private UserRole role;
}
package io.github.onlineshop.security.api.dto;

import lombok.Value;

@Value
public class JwtRequest {
    String username;
    String password;
}

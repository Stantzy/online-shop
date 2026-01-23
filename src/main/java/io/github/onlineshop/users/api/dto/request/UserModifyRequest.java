package io.github.onlineshop.users.api.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.Value;

@Value
public class UserModifyRequest {
    @Nullable
    String username;

    @Nullable
    @Email(message = "Wrong email address format")
    String email;
}

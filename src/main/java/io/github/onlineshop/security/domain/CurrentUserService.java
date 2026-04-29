package io.github.onlineshop.security.domain;

import io.github.onlineshop.security.UserPrincipal;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CurrentUserService {
    public Long getUserId() {
        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new
                AuthenticationCredentialsNotFoundException("Not authenticated");
        }

        Object principal = auth.getPrincipal();

        if(principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getId();
        }

        // Для случая, когда principal - это String (например, после form-login)
        if(principal instanceof String) {
            throw new AuthenticationCredentialsNotFoundException(
                "User not fully loaded. Please login again.");
        }

        throw new AuthenticationCredentialsNotFoundException("Unknown principal type");
    }

    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
    }

    public UserPrincipal getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) auth.getPrincipal();
        }
        return null;
    }
}

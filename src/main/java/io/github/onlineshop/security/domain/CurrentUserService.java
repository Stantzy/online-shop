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

        if(auth == null || !auth.isAuthenticated()) {
            throw new
                AuthenticationCredentialsNotFoundException("Not authenticated");
        }

        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        return userPrincipal.getId();
    }
}

package io.github.onlineshop.security.jwt;

import io.github.onlineshop.security.UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger log =
        LoggerFactory.getLogger(JwtFilter.class);

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        UserPrincipal userPrincipal = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                userPrincipal = jwtTokenUtils.getUserPrincipal(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("The JWT token lifetime has expired");
            }
        }

        if(
            userPrincipal != null &&
            SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                    jwtTokenUtils.getUserPrincipal(jwt),
                    null,
                    jwtTokenUtils
                        .getRoles(jwt).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                );

            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }
}

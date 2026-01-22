package io.github.onlineshop.config;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.security.UserDetailsServiceImpl;
import io.github.onlineshop.security.jwt.JwtFilter;
import io.github.onlineshop.users.database.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.PathPatternRequestMatcherBuilderFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import java.nio.file.Path;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final static Logger log =
        LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Creating PasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailService(UserRepository userRepository) {
        log.info("Creating UserDetailsService bean");
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
        log.info("Creating SecurityFilterChain bean");
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                auth -> {
                    configurePublicEndpoints(auth);
                    configureAuthenticatedEndpoints(auth);
                    configureAdminEndpoints(auth);
                    auth.anyRequest().denyAll();
                }
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authConfig
    ) throws Exception {
        log.info("Creating AuthenticationManager bean");
        return authConfig.getAuthenticationManager();
    }

    private void configurePublicEndpoints(
        AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry auth
    ) {
        // Public auth endpoints
        auth.requestMatchers(HttpMethod.POST, PathConstants.AUTH + "/login").permitAll();
        auth.requestMatchers(HttpMethod.POST, PathConstants.AUTH + "/register").permitAll();

        // Public product endpoints
        auth.requestMatchers(HttpMethod.GET, PathConstants.PRODUCT + "/*").permitAll();
        auth.requestMatchers(HttpMethod.GET, PathConstants.PRODUCT).permitAll();
    }

    private void configureAuthenticatedEndpoints(
        AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry auth
    ) {
        // Auth endpoints
        auth.requestMatchers(HttpMethod.POST, PathConstants.AUTH + "/reset_password").authenticated();

        // Cart endpoints
        auth.requestMatchers(HttpMethod.GET, PathConstants.ORDER + "/cart/items").authenticated();
        auth.requestMatchers(HttpMethod.POST ,PathConstants.ORDER + "/cart/items").authenticated();
        auth.requestMatchers(HttpMethod.DELETE, PathConstants.ORDER + "/cart/items").authenticated();
        auth.requestMatchers(HttpMethod.PUT, PathConstants.ORDER + "/cart/items/**").authenticated();
        auth.requestMatchers(HttpMethod.DELETE, PathConstants.ORDER + "/cart/items/**").authenticated();
        auth.requestMatchers(HttpMethod.GET, PathConstants.ORDER + "/cart/count").authenticated();
        auth.requestMatchers(HttpMethod.GET, PathConstants.ORDER + "/cart/total").authenticated();

        // Order endpoints
        auth.requestMatchers(HttpMethod.POST, PathConstants.ORDER + "/checkout").authenticated();
        auth.requestMatchers(HttpMethod.GET, PathConstants.USER + "/me/orders").authenticated();
        auth.requestMatchers(HttpMethod.GET, PathConstants.USER + "/me/orders/*/status").authenticated();
        auth.requestMatchers(HttpMethod.POST, PathConstants.USER + "/me/orders/*/cancel").authenticated();

        // User endpoints
        auth.requestMatchers(HttpMethod.GET, PathConstants.USER + "/me").authenticated();
        auth.requestMatchers(HttpMethod.PUT, PathConstants.USER + "/me").authenticated();
    }

    private void configureAdminEndpoints(
        AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry auth
    ) {
        // User management endpoints
        auth.requestMatchers(HttpMethod.GET, PathConstants.USER).hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.POST, PathConstants.USER).hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.GET, PathConstants.USER + "/*").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.DELETE, PathConstants.USER + "/*/delete").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.PUT, PathConstants.USER + "/*/update").hasRole("ADMIN");

        // Product management endpoints
        auth.requestMatchers(HttpMethod.POST, PathConstants.PRODUCT).hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.DELETE, PathConstants.PRODUCT + "/*").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.PUT, PathConstants.PRODUCT + "/*").hasRole("ADMIN");

        // Order management endpoints
        auth.requestMatchers(HttpMethod.GET, PathConstants.ORDER).hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.PUT, PathConstants.ORDER + "/*").hasRole("ADMIN");

    }
}
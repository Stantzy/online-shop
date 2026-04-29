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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final static Logger log =
        LoggerFactory.getLogger(WebSecurityConfig.class);

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final JwtFilter jwtFilter;

    public WebSecurityConfig(
        JwtFilter jwtFilter,
        AuthenticationSuccessHandler authenticationSuccessHandler
    ) {
        this.jwtFilter = jwtFilter;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
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
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**", "/auth/**")
            )
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                auth -> {
                    // WEB ENDPOINTS
                    configureWebEndpoints(auth);

                    // REST API ENDPOINTS
                    configureApiEndpoints(auth);
                    auth.requestMatchers("/api/**").authenticated();

                    // OTHER
                    auth.anyRequest().permitAll(); // denyAll();
                }
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/products")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    request -> request.getRequestURI().startsWith("/api")
                )
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

    private void configureWebEndpoints(
        AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry auth
    ) {
        // PUBLIC ENDPOINTS
        auth.requestMatchers(
            "/",
            "/products/**",
            "/auth/**"
        ).permitAll();

        // ADMIN ENDPOINTS
        auth.requestMatchers("/admin/**").hasRole("ADMIN");

        // AUTHENTICATED ENDPOINTS
        auth.requestMatchers(
            "/cart/**",
            "/orders/**",
            "/profile/**"
        ).authenticated();

    }

    private void configureApiEndpoints(
        AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry auth
    ) {
        configureApiPublicEndpoints(auth);
        configureApiAuthenticatedEndpoints(auth);
        configureApiAdminEndpoints(auth);
    }

    private void configureApiPublicEndpoints(
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

    private void configureApiAuthenticatedEndpoints(
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

    private void configureApiAdminEndpoints(
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
        auth.requestMatchers(HttpMethod.DELETE, PathConstants.ORDER + "/*/delete").hasRole("ADMIN");
    }
}
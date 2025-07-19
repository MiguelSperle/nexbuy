package com.miguelsperle.nexbuy.core.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final SecurityFilter securityFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    private static final String[] USER_MODULE_AUTHENTICATED_ENDPOINTS = {
            "/api/v1/users/information",
            "/api/v1/users/password",
            "/api/v1/users/me",
            "/api/v1/addresses",
            "/api/v1/addresses/{addressId}"
    };

    private static final String[] PRODUCT_MODULE_RESTRICTED_ENDPOINTS = {
            "/api/v1/admin/brands",
            "/api/v1/admin/brands/{brandId}",
            "/api/v1/admin/categories",
            "/api/v1/admin/categories/{categoryId}",
            "/api/v1/admin/colors",
            "/api/v1/admin/colors/{colorId}",
            "/api/v1/admin/products",
            "/api/v1/admin/products/{productId}",
            "/api/v1/admin/products/{productId}/status"
    };

    private static final String[] PRODUCT_MODULE_AUTHENTICATED_ENDPOINTS = {
            "/api/v1/brands",
            "/api/v1/categories"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(USER_MODULE_AUTHENTICATED_ENDPOINTS).authenticated()
                                .requestMatchers(PRODUCT_MODULE_RESTRICTED_ENDPOINTS).hasRole("ADMIN")
                                .requestMatchers(PRODUCT_MODULE_AUTHENTICATED_ENDPOINTS).authenticated()
                                .anyRequest().permitAll())
                .exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(this.authenticationEntryPoint).accessDeniedHandler(this.accessDeniedHandler))
                .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

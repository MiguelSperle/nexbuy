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

    private static final String[] USER_MODULE_PUBLIC_ENDPOINTS = {
            "/api/user/create",
            "/api/user/authenticate",
            "/api/user/verification-code/resend",
            "/api/user/confirm-verification",
            "/api/user/refresh-token",
            "/api/user/reset-password/send-recovery-email",
            "/api/user/reset-password/validate-code",
            "/api/user/reset-password"
    };

    private static final String[] PRODUCT_MODULE_RESTRICTED_ENDPOINTS = {
            "/api/product/brand/register",
            "/api/product/brands",
            "/api/product/category/register"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(USER_MODULE_PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(PRODUCT_MODULE_RESTRICTED_ENDPOINTS).hasRole("ADMIN")
                                .anyRequest().authenticated())
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

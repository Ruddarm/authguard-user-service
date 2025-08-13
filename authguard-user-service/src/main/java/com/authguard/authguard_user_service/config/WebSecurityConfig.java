package com.authguard.authguard_user_service.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.authguard.authguard_user_service.filters.ServiceJwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final ServiceJwtAuthFilter serviceJwtAuthFilter;

    private HttpSecurity disableCsrfAndCors(HttpSecurity http) throws Exception {
        return http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable());
    }

    @Bean
    @Order(1)
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher("/user/**")
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/user/**", "/auth/user/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(crsfConfig -> crsfConfig.disable());
        return httpSecurity.build();
    }

    @Bean
    @Order(2) // Inter-service first
    public SecurityFilterChain interServiceFilterChain(HttpSecurity http) throws Exception {
        disableCsrfAndCors(http)
                .securityMatcher("/service/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(serviceJwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8081"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

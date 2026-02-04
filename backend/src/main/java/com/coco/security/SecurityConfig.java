package com.coco.security;

import com.coco.security.jwt.JwtAuthFilter;
import com.coco.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // Swagger / OpenAPI
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Actuator
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()

                        // Auth (MVP)
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Todo lo demás protegido
                        .anyRequest().authenticated()
                )

                // Importante: el filtro JWT va antes del filtro estándar de user/pass
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /**
     * CORS configurado por properties:
     * coco.cors.allowed-origins=http://localhost:4200,http://127.0.0.1:4200
     * coco.cors.allowed-methods=GET,POST,...
     * coco.cors.allowed-headers=Authorization,Content-Type
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource(org.springframework.core.env.Environment env) {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(splitCsv(env.getProperty("coco.cors.allowed-origins", "")));
        config.setAllowedMethods(splitCsv(env.getProperty("coco.cors.allowed-methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS")));
        config.setAllowedHeaders(splitCsv(env.getProperty("coco.cors.allowed-headers", "Authorization,Content-Type")));
        config.setAllowCredentials(Boolean.parseBoolean(env.getProperty("coco.cors.allow-credentials", "true")));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private static List<String> splitCsv(String raw) {
        if (raw == null || raw.isBlank()) return List.of();
        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.example.demo.blueprint.security;

import com.example.demo.blueprint.auth.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFilter authFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {

        return http
        .csrf((AbstractHttpConfigurer::disable))
        .authorizeHttpRequests((authz) -> authz
            .requestMatchers("auth/**").permitAll()
            .requestMatchers("/storage/**", "/password/**", "/user/**").hasAnyRole("USER")
            .anyRequest().permitAll()
        )
        .sessionManagement((session) -> session
            .sessionCreationPolicy(STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

}

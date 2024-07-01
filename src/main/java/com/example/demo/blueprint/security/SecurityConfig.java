package com.example.demo.blueprint.security;

import com.example.demo.blueprint.auth.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

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

        System.out.println("Filter starts");

        return http
        .csrf((csrf) -> csrf
            .disable()
        )
        .securityMatchers((matchers) -> matchers
        // Which ones must be secured
            .requestMatchers("/auth/**")
            .requestMatchers("/demo")
            .requestMatchers("/course/**")
        )
        .authorizeHttpRequests((authz) -> authz
        // How we secure those ones

            .requestMatchers("/**").permitAll()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/course/**").hasAuthority("ADMIN")
            .requestMatchers("/chapter/**").hasAuthority("ADMIN")
            .requestMatchers("/lesson/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
        )
        .sessionManagement((session) -> session
            .sessionCreationPolicy(STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

}

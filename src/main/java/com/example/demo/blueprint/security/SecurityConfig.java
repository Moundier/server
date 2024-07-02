package com.example.demo.blueprint.security;

import com.example.demo.blueprint.auth.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFilter authFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {

        return http
        .csrf((csrf) -> csrf
            .disable()
        )
        // .securityMatchers(matcher -> matcher
            // // Toggle down
            // .requestMatchers("/storage/**")
            // .requestMatchers("/password/**")
            // .requestMatchers("/user/**")
        // )
        .authorizeHttpRequests((authz) -> authz
            // How we secure those ones

            .requestMatchers("auth/**").permitAll()
            // .requestMatchers(HttpMethod.GET, "/storage/**").hasAnyRole("USER")
            // .requestMatchers("/password/**").hasAnyRole("USER")
            // .requestMatchers("/user/**").hasAnyRole("USER")
            // // Toggle up
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

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

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFilter authFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {

        System.out.println("Filter starts");

        // TODO: secure routes based on authorities

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

            // Can POST
            .requestMatchers(HttpMethod.POST ,"/**").permitAll()
            .requestMatchers(HttpMethod.POST ,"/auth/**").permitAll()
            // .requestMatchers(HttpMethod.OPTIONS ,"/auth/**").permitAll()
            // Course POST by ROLE_ADMIN
            .requestMatchers(HttpMethod.POST, "/course/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/course/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/course/**").hasAuthority("ADMIN")
            // Chapter POST by ROLE_ADMIN
            .requestMatchers(HttpMethod.POST, "/chapter/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/chapter/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/chapter/**").hasAuthority("ADMIN")
            // Lesson POST by ROLE_ADMIN
            .requestMatchers(HttpMethod.POST, "/lesson/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/lesson/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/lesson/**").hasAuthority("ADMIN")
            // Only authenticated (ROLE_USER, ROLE_ADMIN)
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

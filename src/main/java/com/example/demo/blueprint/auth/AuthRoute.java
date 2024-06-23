package com.example.demo.blueprint.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.user.User;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthRoute {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?>register(@Valid @RequestBody RegisterDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        return authService.login(request);            
    }

    @PostMapping("/privilege")
    public ResponseEntity<?> privilege(@RequestBody RegisterDTO request) {
        return authService.privilege(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return authService.refreshToken(authHeader);
    }

    // Todo: make the following private

    public static record LoginDTO(
        String email,
        String password
    ) { }

    public static record SignInResponse(
        User user,
        TokensResponse tokens
    ) { }

    @Builder
    public static record RegisterDTO(
        String firstName,
        String lastName,
        String email,
        String password
    ) { }

    public static record ErrorDTO (
        String error,
        String message
    ) { }

    public static record TokensResponse(
        String accessToken,
        String refreshToken
    ) { }

}

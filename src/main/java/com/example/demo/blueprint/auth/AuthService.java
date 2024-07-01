package com.example.demo.blueprint.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.entities.user.Role;
import com.example.demo.entities.user.User;
import com.example.demo.entities.user.UserRepo;
import com.example.demo.blueprint.auth.AuthRoute.LoginDTO;
import com.example.demo.blueprint.auth.AuthRoute.RegisterDTO;

import lombok.RequiredArgsConstructor;

import static com.example.demo.blueprint.auth.AuthRoute.TokensResponse;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepo userRepo;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<?> register(RegisterDTO request) {

    Optional<User> found = userRepo.findByEmail(request.email());

    if (found.isPresent()) {
      final String error = "Provided email is already in use.";
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    var user = User.builder()
      .firstName(request.firstName())
      .lastName(request.lastName())
      .email(request.email())
      .password(encodePassword(request.password()))
      .role(Role.USER)
      .termsAcceptedDate(LocalDateTime.now().toString())
      .tutorialComplete(false)
      .build();

    userRepo.save(user);

    var accessToken = jwtService.generateAccessToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    return ResponseEntity.status(HttpStatus.OK).body(
      new TokensResponse(accessToken, refreshToken));
  }

  public String encodePassword(String content) {
    return passwordEncoder
      .encode(content)
      .toString();
  }

  public ResponseEntity<?> login(LoginDTO request) {
    Optional<User> user = userRepo.findByEmail(request.email());

    if (user.isEmpty()) {
      String errorMessage = "User not found for email: " + request.email();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.email(),
        request.password()
      )
    );

    String accessToken = jwtService.generateAccessToken(user.get()); // NOTE: User implements
    String refreshToken = jwtService.generateRefreshToken(user.get()); // Todo: refactor in fronted

    TokensResponse tokensResponse = new TokensResponse(accessToken, refreshToken); 

    return ResponseEntity.status(HttpStatus.OK).body(tokensResponse);
  }

  public ResponseEntity<?> privilege(RegisterDTO request) {

    var user = User.builder()
        .firstName(request.firstName())
        .lastName(request.lastName())
        .email(request.email())
        .password(encodePassword(request.password()))
        .role(Role.ADMIN)
        .build();

    if (user != null) userRepo.save(user);

    var accessToken = jwtService.generateAccessToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    return ResponseEntity.status(HttpStatus.OK).body(
        new TokensResponse(accessToken, refreshToken));
  }

  public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

    final String refreshToken;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.badRequest().build();
    }

    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractSubject(refreshToken);

    if (userEmail != null) {

      Optional<User> userDetails = this.userRepo.findByEmail(userEmail);

      if (jwtService.isTokenValid(refreshToken, userDetails.get())) {

        String accessToken = jwtService.generateAccessToken(userDetails.get());
        TokensResponse response = new TokensResponse(accessToken, refreshToken);
        return ResponseEntity.ok(response);
      }
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

}

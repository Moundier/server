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
import com.example.demo.helpers.Jsonify;
import com.example.demo.helpers.Colorify;
import com.example.demo.blueprint.auth.AuthRoute.LoginDTO;
import com.example.demo.blueprint.auth.AuthRoute.RegisterDTO;

import lombok.RequiredArgsConstructor;

import static com.example.demo.blueprint.auth.AuthRoute.ErrorDTO;
import static com.example.demo.blueprint.auth.AuthRoute.TokensResponse;
import static com.example.demo.blueprint.auth.AuthRoute.SignInResponse;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<?> register(RegisterDTO request) {

    User found = userRepo.findByEmail(request.email());

    if (found != null) {
      final String error = "Provided email is already in use.";
      final String message = "Please, navigate to the login section.";
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(error, message));
    }

    var user = User.builder()
      .firstName(request.firstName())
      .lastName(request.lastName())
      .email(request.email())
      .password(encodePassword(request.password()))
      .role(Role.USER)
      .termsAcceptedDate(new Date(System.currentTimeMillis()))
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
    var user = userRepo.findByEmail(request.email());

    // Authentication auth = authenticationManager.authenticate(
    //   new UsernamePasswordAuthenticationToken(
    //     request.email(),
    //     request.password()
    //   )
    // );  // Note: dont tell the password is invalid, because it compromises email

    // if (!auth.isAuthenticated()) {
    //   String message = "Unauthenticated user";
    //   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    // }

    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.email(),
        request.password()
      )
    );

    String accessToken = jwtService.generateAccessToken(user); // NOTE: User implements
    String refreshToken = jwtService.generateRefreshToken(user); // Todo: refactor in fronted

    TokensResponse tokensResponse = new TokensResponse(accessToken, refreshToken); 
    SignInResponse signInResponse = new SignInResponse(user, tokensResponse);

    String userString = Jsonify.toString(user);
    Colorify.info("LOGIN USER", userString);

    return ResponseEntity.status(HttpStatus.OK).body(signInResponse);
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

    // Output.info("Refresh Token Performed");
    final String refreshToken;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.badRequest().build();
    }

    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);

    if (userEmail != null) {

      User userDetails = this.userRepo.findByEmail(userEmail);

      if (jwtService.isTokenValid(refreshToken, userDetails)) {

        String accessToken = jwtService.generateAccessToken(userDetails);
        TokensResponse response = new TokensResponse(accessToken, refreshToken);
        // System.out.println(response);
        return ResponseEntity.ok(response);
      }
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

}

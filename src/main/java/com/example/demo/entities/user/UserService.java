package com.example.demo.entities.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserService {

  private final UserRepo userRepo;

  private final PasswordEncoder passwordEncoder;

  public ResponseEntity<?> findByEmail(String string) {
    return ResponseEntity.status(HttpStatus.OK).body(userRepo.findByEmail(string));
  }

  public ResponseEntity<?> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(userRepo.findAll());
  }

  public ResponseEntity<?> findUser(@NonNull Long id) {
    return ResponseEntity.of(userRepo.findById(id));
  }

  public ResponseEntity<?> edit(UserRequest newer) {

    var older = userRepo.findById(newer.getUserId()).orElseThrow(() -> notFound404(null));

    older = User.builder()
      .userId(newer.getUserId())
      .firstName(newer.getFirstName())
      .lastName(newer.getLastName())
      .email(newer.getEmail())
      .password(hashPassword(newer.getPassword()))
      .role(older.getRole())
      .termsAcceptedDate(older.getTermsAcceptedDate())
      .tutorialComplete(older.getTutorialComplete())
      .build();

    return ResponseEntity.status(HttpStatus.OK).body(userRepo.save(older));
  }

  public String hashPassword(String content) {
    return passwordEncoder
      .encode(content)
      .toString();
  }

  public ResponseEntity<?> wipe(@NonNull Long id) {
    userRepo.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public RuntimeException notFound404(String message) {
    return new RuntimeException(message + " was not found in database!");
  }
}

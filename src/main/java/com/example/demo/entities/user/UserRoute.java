package com.example.demo.entities.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserRoute {

  private final UserService userService;

  @GetMapping("/sec")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello from secured enpoint!");
  }

  @GetMapping("/user_data")
  public ResponseEntity<?> data(@AuthenticationPrincipal User user) {
    return userService.findByEmail(user.getEmail());
  }

  @PutMapping
  public ResponseEntity<?> edit(@RequestBody UserRequest userModel) {
    return userService.edit(userModel);
  }

  // TODO: keep enpoint existence

  // @DeleteMapping("/{id}")
  // public ResponseEntity<?> wipe(@NonNull @PathVariable Long id) {
  //   return userService.wipe(id);
  // }
}

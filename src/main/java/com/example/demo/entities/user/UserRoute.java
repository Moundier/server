package com.example.demo.entities.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.helpers.Colorify;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserRoute {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> test() {
    Map<String, Object> json = new HashMap<>();
    json.put("url", "localhost:9090");
    json.put("is_application_running", true);
    json.put("ipv4", "192.168.0.10");
    return ResponseEntity.status(HttpStatus.OK).body(json);
  }

  @PostMapping
  public ResponseEntity<?> getUserById(@RequestBody User user) {
    System.out.println(user);
    return userService.findById(user);
  }

  @GetMapping("/user_data")
  public ResponseEntity<?> data(@AuthenticationPrincipal User user) {
    return userService.findByEmail(user.getEmail());
  }

  @PutMapping
  public ResponseEntity<?> updateUser(@RequestBody User user) {
    Colorify.update(user.toString());
    return userService.updateUser(user);
  }

}

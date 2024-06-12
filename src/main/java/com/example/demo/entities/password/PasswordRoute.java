package com.example.demo.entities.password;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordRoute {
    
    private final PasswordService service;

    @PostMapping("/create")
    public ResponseEntity<?> createPassword() {
        return ResponseEntity.ok(service);
    }

    @GetMapping("/locate")
    public ResponseEntity<?>locatePassword() {
        return ResponseEntity.ok(service);
    }

    @PutMapping
    public ResponseEntity<?>updatePassword() {
        return ResponseEntity.ok(service);
    }

    @DeleteMapping
    public ResponseEntity<?>deletePassword() {
        return ResponseEntity.ok(service);
    }

}

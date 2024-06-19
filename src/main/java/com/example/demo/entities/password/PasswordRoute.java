package com.example.demo.entities.password;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.storage.Storage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordRoute {
    
    private final PasswordService service;

    @PostMapping
    public ResponseEntity<?> createStorage(@RequestBody Password storage) {
        return service.createPassword(storage);
    }

    @GetMapping
    public ResponseEntity<?>locatePassword(@RequestBody Storage user) {
        return service.locatePassword(user);
    }

    @PostMapping("/all")
    public ResponseEntity<?>locateAllPassword(@RequestBody Storage user) {
        return service.locatePassword(user);            
    } 

    @PutMapping
    public ResponseEntity<?>updateStorage() {
        return service.updatePassword();
    }

    @DeleteMapping
    public ResponseEntity<?>deleteStorage(@RequestBody Password storage) {
        return service.deletePassword(storage);
    }
}

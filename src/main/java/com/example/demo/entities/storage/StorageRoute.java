package com.example.demo.entities.storage;

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
@RequestMapping("storage")
public class StorageRoute {

    private final StorageService service;
    
    @PostMapping
    public ResponseEntity<?> createStorage(Storage storage) {
        return service.createStorage(storage);
    }

    @GetMapping
    public ResponseEntity<?>locateStorage() {
        return ResponseEntity.ok(service);
    }

    @GetMapping("/all")
    public ResponseEntity<?>locateAllStorage() {
        return ResponseEntity.ok(service);
    } 

    @PutMapping
    public ResponseEntity<?>updateStorage() {
        return ResponseEntity.ok(service);
    }

    @DeleteMapping
    public ResponseEntity<?>deleteStorage() {
        return ResponseEntity.ok(service);
    }
}

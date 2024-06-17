package com.example.demo.entities.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("storage")
public class StorageRoute {

    private final StorageService service;
    
    @PostMapping
    public ResponseEntity<?> createStorage(@RequestBody Storage storage) {
        
        System.out.println("Received storage object: " + storage.toString());
        return service.createStorage(storage);
    }

    @GetMapping
    public ResponseEntity<?>locateStorage(@RequestBody User user) {
        return service.locateStorage(user);
    }

    @PostMapping("/all")
    public ResponseEntity<?>locateAllStorage(@RequestBody User user) {
        System.out.println(user);
        try {
            return service.localAllStorage(user);            
        } catch (Exception e) {
            System.out.println("WHERE ");
            System.out.println(e.getMessage());
        }
        return null;
    } 

    @PutMapping
    public ResponseEntity<?>updateStorage() {
        return service.updateStorage(null);
    }

    @DeleteMapping
    public ResponseEntity<?>deleteStorage() {
        return service.deleteStorage(null);
    }
}

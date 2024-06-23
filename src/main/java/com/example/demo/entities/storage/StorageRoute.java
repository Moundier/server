package com.example.demo.entities.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.user.User;
import com.example.demo.helpers.Jsonify;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("storage")
public class StorageRoute {

    private final StorageService service;
    
    @PostMapping
    public ResponseEntity<?> createStorage(@RequestBody Storage storage) {
        return service.createStorage(storage);
    }

    @PostMapping("/all")
    public ResponseEntity<?>locateAllStorage(@RequestBody User user) {
        return service.localAllStorage(user);            
    } 

    @PutMapping
    public ResponseEntity<?>updateStorage(@RequestBody Storage storage) {
        Jsonify.toString(storage);

        return service.updateStorage(storage);
    }

    @DeleteMapping
    public ResponseEntity<?>deleteStorage(@RequestBody Storage storage) {
        return service.deleteStorage(storage);
    }
}

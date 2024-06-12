package com.example.demo.entities.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService  {
    
    private final StorageRepo repo;

    public ResponseEntity<?> createStorage(Storage storage) {
        return ResponseEntity.ok(this.repo.save(storage));
    }

    public void updateStorage() {

    }

    public void locateStorage() {

    }

    public void localAllStorage() {
        return;
    }

    public void deleteStorage() {

    }
}

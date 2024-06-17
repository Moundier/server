package com.example.demo.entities.storage;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService  {
    
    private final StorageRepo repo;

    public ResponseEntity<?> createStorage(Storage storage) {
        return ResponseEntity.ok(this.repo.save(storage));
    }

    public ResponseEntity<?> updateStorage(Storage storage) {

        Optional<Storage> exists = repo.findById(storage.getId());

        if (exists.isPresent()) {
            storage = Storage.builder()
            .id(storage.getId())
            .label(storage.getLabel())
            .note(storage.getNote())
            .passwords(storage.getPasswords())
            .user(storage.getUser())
            .build();

            return ResponseEntity.ok(this.repo.save(storage));
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> locateStorage(User user) {
        return ResponseEntity.status(HttpStatus.OK).body(repo.findByUser(user));
    }

    public ResponseEntity<?> localAllStorage(User user) {
        return ResponseEntity.status(HttpStatus.OK).body(repo.findByUser(user));
    }

    public ResponseEntity<?> deleteStorage(Storage storage) {
        repo.delete(storage);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

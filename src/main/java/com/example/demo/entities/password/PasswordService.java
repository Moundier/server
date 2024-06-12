package com.example.demo.entities.password;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.storage.Storage;
import com.example.demo.entities.storage.StorageRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {
    
    private final StorageRepo storageRepo;
    private final PasswordRepo passwordRepo;

    public ResponseEntity<?> createPassword(PasswordEntry payload) {

        Optional<Storage> optionalStorage = storageRepo.findById(payload.storage().getId());

        if (optionalStorage.isEmpty()) return ResponseEntity.notFound().build();

        Storage storage = optionalStorage.get();

        Password password = Password.builder()
        .label(payload.password().getLabel())
        .password(payload.password().getPassword())
        .storage(storage)
        .build();

        storage.getPasswords().add(password);
        storageRepo.save(storage);

        return ResponseEntity.ok(this.passwordRepo.save(password));
    }

    public void updatePassword() {

    }

    public void locatePassword() {

    }

    public void deletePassword() {

    }
}

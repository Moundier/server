package com.example.demo.entities.password;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.storage.Storage;
import com.example.demo.helpers.Cipherify;
import com.example.demo.helpers.Colorify;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {
    
    private final Cipherify cipher;
    private final PasswordRepo repo;

    public ResponseEntity<?> createPassword(Password password) {

        Colorify.create("Password");

        Password entity = Password.builder()
        .tag(cipher.encrypt(password.getTag()))
        .title(cipher.encrypt(password.getTitle()))
        .storage(password.getStorage())
        .build();

        Password obj = this.repo.save(entity);
        obj.setTag(cipher.decrypt(obj.getTag()));
        obj.setTitle(cipher.decrypt(obj.getTitle()));

        return ResponseEntity.ok(obj);
    }

    public ResponseEntity<?> updatePassword(Password password) {
        
        Colorify.update("Password");
        Optional<Password> older = repo.findById(password.getId());

        if (older.isPresent()) {
            password = Password.builder()
            .id(password.getId())
            .tag(cipher.encrypt(password.getTag()))
            .title(cipher.encrypt(password.getTitle()))
            .storage(password.getStorage())
            .build();

            Password obj = this.repo.save(password);
            obj.setTag(cipher.decrypt(obj.getTag()));
            obj.setTitle(cipher.decrypt(obj.getTitle()));

            return ResponseEntity.ok(obj);
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> locatePassword(Storage storage) {
        Colorify.locate("Password");
        
        List<Password> passwords = repo.findByStorage(storage)
        .stream().map((password) -> Password.builder()
            .id(password.getId())
            .tag(cipher.decrypt(password.getTag()))
            .title(cipher.decrypt(password.getTitle()))
            .storage(password.getStorage())
            .build()
        )
        .toList();

        return ResponseEntity.status(HttpStatus.OK).body(passwords);
    }

    public ResponseEntity<?> deletePassword(Password password) {
        Colorify.delete("Password");
        repo.delete(password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

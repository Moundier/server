package com.example.demo.entities.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.user.User;
import com.example.demo.helpers.Cipherify;
import com.example.demo.helpers.Colorify;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService  {
    
    private final StorageRepo repo;
    private final Cipherify cipher;

    public ResponseEntity<?> createStorage(Storage storage) {
        
        Colorify.create("Storage");

        Storage entity = Storage.builder()
        .tag(cipher.encrypt(storage.getTag()))
        .title(cipher.encrypt(storage.getTitle()))
        .user(storage.getUser())
        .build();
        
        Storage obj = this.repo.save(entity);
        obj.setTag(cipher.decrypt(obj.getTag()));
        obj.setTitle(cipher.decrypt(obj.getTitle()));

        return ResponseEntity.ok(obj);
    }

    public ResponseEntity<?> localAllStorage(User user) {
        
        Colorify.locate("Locate");
        
        List<Storage> storages = repo.findByUser(user)
        .stream().map((storage) -> Storage.builder()
            .id(storage.getId())
            .tag(cipher.decrypt(storage.getTag()))
            .title(cipher.decrypt(storage.getTitle()))
            .user(storage.getUser())
            .build()
        )
        .toList();

        return ResponseEntity.status(HttpStatus.OK).body(storages);
    }

    public ResponseEntity<?> updateStorage(Storage storage) {

        Colorify.update("Storage");
        Optional<Storage> exists = repo.findById(storage.getId());

        if (exists.isPresent()) {
            storage = Storage.builder()
            .id(storage.getId())
            .tag(storage.getTag())
            .title(storage.getTitle())
            .user(storage.getUser())
            .build();

            return ResponseEntity.ok(this.repo.save(storage));
        }

        return ResponseEntity.notFound().build();
    }
    
    public ResponseEntity<?> deleteStorage(Storage storage) {
        Colorify.delete("Delete");
        repo.deleteById(storage.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

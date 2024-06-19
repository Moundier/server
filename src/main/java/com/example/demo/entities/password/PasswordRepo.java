package com.example.demo.entities.password;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.storage.Storage;

@Repository
public interface PasswordRepo extends JpaRepository<Password, Long> {
    
  List<Password> findByStorage(Storage storage);
}

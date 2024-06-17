package com.example.demo.entities.storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.user.User;

@Repository
public interface StorageRepo extends JpaRepository<Storage, Long> {
    
  List<Storage> findByUser(User user);
}

package com.example.demo.blueprint;

import org.springframework.http.ResponseEntity;

public interface SfewContract<T> {
    // TODO: no real use, just to speed up development
    ResponseEntity<?> save(T entity);
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> findAll();
    ResponseEntity<?> edit(Long id, T entity);
    ResponseEntity<?> wipe(Long id);
}
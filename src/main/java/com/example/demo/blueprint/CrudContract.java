package com.example.demo.blueprint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public abstract class CrudContract<T> {

    @PostMapping
    abstract ResponseEntity<?> create(T entity);

    @GetMapping
    abstract ResponseEntity<?> locateById(Long id);

    @GetMapping("/all")
    abstract ResponseEntity<?> locateAll();

    @PutMapping
    abstract ResponseEntity<?> update(Long id, T entity);

    @DeleteMapping
    abstract ResponseEntity<?> delete(Long id);
}
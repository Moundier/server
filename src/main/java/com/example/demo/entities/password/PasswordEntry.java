package com.example.demo.entities.password;

import com.example.demo.entities.storage.Storage;

public record PasswordEntry(
    Storage storage,
    Password password
) {  } 
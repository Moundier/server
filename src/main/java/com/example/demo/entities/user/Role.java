package com.example.demo.entities.user;

public enum Role {
    USER, /* Have limited access, can interact */
    GUEST, /* Have limited access, cant interact */
    ADMIN, /* Have complete access and can modify */
    CREATOR, /* Have limited access and can modify */
}

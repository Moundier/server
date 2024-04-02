package com.example.demo.entities.user;

import java.util.Date;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserRequest {
  
    @NonNull
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Date termsAcceptedDate;
    private Boolean tutorialComplete;
}

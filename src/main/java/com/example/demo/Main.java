package com.example.demo;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.entities.user.Role;
import com.example.demo.entities.user.User;
import com.example.demo.blueprint.auth.AuthService;
import com.example.demo.blueprint.auth.AuthRoute.RegisterDTO;

@SpringBootApplication
public class Main {

  @Autowired
  private AuthService authService;

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Bean
  CommandLineRunner runner() {

    return args -> {

      // String template = """
      // REPORT
      // ---
      // Server: %s
      // Author: %s
      // """;

      // var result = template.formatted("Gabriel Vieira Casanova", "https://localhost:8080");

      // CoreString.logBox(result);

      List<User> USERS = List.of(
        new User(null, "nami", "namizo", "nami@gmail.com", "1234", null, new Date(0), false),
        new User(null, "usop", "sogeking", "usop@gmail.com", "1234", null, new Date(0), false),
        new User(null, "zoro", "roronoa", "zoro@gmail.com", "1234", Role.ADMIN, new Date(0), false)
      );

      List<User> ADMINS = List.of(
        new User(null, "luff", "monkey", "luff@gmail.com", "1234", Role.ADMIN, new Date(0), false)
      );

      for (User user : USERS) {

        RegisterDTO response = new RegisterDTO(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPassword());

        authService.register(response);
      }

      for (User admin : ADMINS) {

        RegisterDTO response = new RegisterDTO(
          admin.getFirstName(),
          admin.getLastName(),
          admin.getEmail(),
          admin.getPassword());

        authService.register(response);
      }
    };
  }
}

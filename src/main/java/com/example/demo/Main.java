package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.blueprint.auth.AuthService;
import com.example.demo.blueprint.auth.AuthRoute.RegisterDTO;
import com.example.demo.helpers.Colorify;

@SpringBootApplication
public class Main {

  @Autowired
  private AuthService authService;

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
    Colorify.info("System Server", "");
  }

  @Bean
  CommandLineRunner runner() {
    return args -> {

      List<String> names = List.of("nami", "zoro", "usop","luff");

      for (String name: names) {
        final var email = name + "@gmail.com";
        final var extra = "__";
        final var passw = "1234";
        authService.register(new RegisterDTO(name, extra, email , passw));
      }

    };
  }
}

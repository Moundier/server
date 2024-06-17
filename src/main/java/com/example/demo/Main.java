package com.example.demo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

      LocalTime time = LocalTime.now();
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm:ss");
      String log = String.format(":: Current time :: %s", time.format(pattern));
      System.out.println(log);

      List<String> strs = List.of("nami", "zoro", "usop");

      for (String name: strs) {
        RegisterDTO response = new RegisterDTO(name, "__", name + "@gmail.com", "1234");
        authService.register(response);
      }

    };
  }
}

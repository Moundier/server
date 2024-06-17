package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.SneakyThrows;

public class JacksonSerializer {

  private static final ObjectMapper om = new ObjectMapper();

  static {
      om.enable(SerializationFeature.INDENT_OUTPUT);
  }

  @SneakyThrows
  public static <T> String toString(T object) {
      return om.writeValueAsString(object);
  }
}

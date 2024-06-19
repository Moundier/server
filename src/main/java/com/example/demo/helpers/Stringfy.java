package com.example.demo.helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Stringfy {

  public static String createSlug(String string) {
    if (isNullOrEmpty(string)) {
      throw new RuntimeException("This " + (string) + " value is null or empty");
    }

    return string.toLowerCase().replaceAll(" ", "-");
  }

  public static boolean isNullOrEmpty(String string) {
    return string == null || string.isEmpty();
  }

  public static String getSlug(String string) {
    return string.toLowerCase().replace(" ", "-");
  }

  public static String abbreviateString(String str, int maxLength) {
    if (str.length() <= maxLength) {
      return str;
    } else {
      return str.substring(0, maxLength - 3) + "...";
    } 
  }

  public static class CoreString<T> {

    private static final String RED = "\u001B[31m"; // Red
    private static final String GREEN = "\u001B[95m"; // Green
    private static final String YELLOW = "\u001B[33m"; // Yellow
    private static final String BLUE = "\u001B[92m"; // Blue
    private static final String PURPLE = "\u001B[35m"; // Purple
    private static final String RESET = "\u001B[0m"; // Reset to default color

    private static final String STRING_PURP = "\u001B[38;5;20m"; // Grey
    private static final String STRING_BLUE = "\u001B[38;5;44m"; // Grey

    private static ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
    private static LocalDate localDate = LocalDate.now();
    private static LocalTime localTime = LocalTime.now();

    private static ZonedDateTime onZone = ZonedDateTime.of(localDate, localTime, zoneId);
    
    private static FormatStyle styled = FormatStyle.MEDIUM;
    private static String timeFormatter = DateTimeFormatter.ofLocalizedDateTime(styled).format(onZone);

    public static void onFail(String string) {
      string = "%sFAIL%s %s".formatted(timeFormatter, RED, RESET, string);
      System.out.println(string);
    }
  
    public static void onDone(String string) {
      string = "[%s] %sDONE%s %s%s%s".formatted(timeFormatter, BLUE, RESET, STRING_BLUE, string, RESET);
      System.out.println(string);
    }
  
    public static void onInfo(String string) {
      string = "[%s] %sINFO%s %s%s%s".formatted(timeFormatter, GREEN, RESET, STRING_PURP, string, RESET);
      System.out.println(string);
    }
  
    public static void inHere(String string) {
      string = "[%s] %sHERE%s %s".formatted(timeFormatter, YELLOW, RESET, string);
      System.out.println(string);
    }
  
    public static void onWarn(String string) {
      string = "[%s] %sWARN%s %s".formatted(timeFormatter, YELLOW, RESET, string);
      System.out.println(string);
    }
  
    public static void custom(String string) {
      string = "[%s] %sCUSTOM%s %s".formatted(timeFormatter, PURPLE, RESET, string);
      System.out.println(string);
    }
  
    public static void log(String message) {
      System.out.println(message);
    }
  
    public static void makeTable(String message) {
      List<String> lines = Arrays.asList(message.split("\n"));
  
      int width = lines.stream().max(Comparator.comparingInt(String::length)).get().length();
  
      System.out.println("╭" + "─".repeat(width + 2) + "╮");
  
      for (String line : lines) {
  
        if (line.startsWith("---") || line.endsWith("---")) {
          System.out.println("├" + "─".repeat(width + 2) + "┤");
          continue;
        }
  
        System.out.println("│ " + ("%-" + width + "s").formatted(line) + " │");
      }
  
      System.out.println("╰" + "─".repeat(width + 2) + "╯");
    }
  }
}


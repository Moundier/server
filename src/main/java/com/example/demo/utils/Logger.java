package com.example.demo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String getCurrentTime() {
        return LocalDateTime.now().format(formatter);
    }

    // ANSI escape codes for colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

    public static void info(String tag, String message) {
        System.out.println(getColoredPrefix("INFO", ANSI_BLUE) + " " + getCurrentTime() + " [" + tag + "] " + message);
    }

    public static void debug(String tag, String message) {
        System.out.println(getColoredPrefix("DEBUG", ANSI_PURPLE) + " " + getCurrentTime() + " [" + tag + "] " + message);
    }

    public static void warning(String tag, String message) {
        System.out.println(getColoredPrefix("WARNING", ANSI_YELLOW) + " " + getCurrentTime() + " [" + tag + "] " + message);
    }

    public static void error(String tag, String message) {
        System.err.println(getColoredPrefix("ERROR", ANSI_RED) + " " + getCurrentTime() + " [" + tag + "] " + message);
    }

    private static String getColoredPrefix(String prefix, String color) {
        return "\n" + color + prefix + ANSI_RESET;
    }

}

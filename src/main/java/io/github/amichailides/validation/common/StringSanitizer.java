package io.github.amichailides.validation.common;

public class StringSanitizer {
    private StringSanitizer(){};


    public static String clean(String input) {
        if (input == null || input.isBlank()) return "";
        return input.trim();
    }


}

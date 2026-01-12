package io.github.amichailides.validation.sanitizers;

public class NameSanitizer {
    private NameSanitizer(){};


    public static String clean(String input) {
        if (input == null || input.isBlank()) return "";
        return input.trim();
    }


}

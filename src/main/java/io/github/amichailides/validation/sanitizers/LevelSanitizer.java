package io.github.amichailides.validation.sanitizers;

public class LevelSanitizer {
    private LevelSanitizer(){};

    public static String clean(String input) {
        if (input == null || input.isBlank()) return  "";
        return input.trim().toUpperCase();
    }
}

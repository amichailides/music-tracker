package io.github.amichailides.validation.student;

public class LevelSanitizer {
    private LevelSanitizer(){};

    public static String clean(String input) {
        if (input == null || input.isBlank()) return  "";
        return input.trim().toUpperCase();
    }
}

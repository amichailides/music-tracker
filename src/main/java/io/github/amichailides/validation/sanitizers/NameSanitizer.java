package io.github.amichailides.validation.sanitizers;

public class NameSanitizer {
    public static String clean(String input) {
        if (input == null || input.isBlank()) return "";

        String trimmed = input.trim();
        if (trimmed.isEmpty()) return "";

        return trimmed.substring(0,1).toUpperCase() +
                trimmed.substring(1).toLowerCase();
    }
}

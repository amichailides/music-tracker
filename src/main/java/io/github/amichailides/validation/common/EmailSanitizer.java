package io.github.amichailides.validation.common;

public class EmailSanitizer {
    public static String clean(String input) {
        if (input == null) return "";


        // 3. replaceAll("\\s", "") -> removes whitespaces between
        return input.trim().toLowerCase().replaceAll("\\s", "");
    }
}

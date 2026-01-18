package io.github.amichailides.validation.common;

public class IntegerSanitizer {
    public static String clean(String input){
        return  (input  == null || input.isBlank()) ? "" : input.trim();

    }
}

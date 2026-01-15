package io.github.amichailides.validation.student;

public class IdSanitizer {
    public static String clean(String input){
        return  (input  == null || input.isBlank()) ? "" : input.trim();

    }
}

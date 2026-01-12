package io.github.amichailides.validation.validators;

public class MobileValidator {
    public static boolean isValid(String input) {
        if (input == null) return false;


        return input.matches("^69\\d{8}$");
    }
}

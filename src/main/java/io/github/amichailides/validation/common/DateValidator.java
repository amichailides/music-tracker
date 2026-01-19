package io.github.amichailides.validation.common;

import java.util.Optional;

public class DateValidator {
    public static Optional<String> isValid(String input) {
        if (input == null || input.isBlank()) return Optional.empty();

        try {
            java.time.LocalDate.parse(input); // Αν δεν ειναι YYYY-MM-DD, πεταει Exception
            return Optional.of(input);
        } catch (java.time.format.DateTimeParseException e) {
            return Optional.empty();
        }
    }
}

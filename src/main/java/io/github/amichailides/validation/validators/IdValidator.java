package io.github.amichailides.validation.validators;

import io.github.amichailides.validation.ValidationConstants;

import java.util.Optional;

public class IdValidator {
    private IdValidator() {
    }

    public static Optional<Long> validateAndParse(String input) {
        if (isInvalidFormat(input)) {
            return Optional.empty();
        }

        try {
            long value = Long.parseLong(input);
            return (value > 0) ? Optional.of(value) : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static boolean isInvalidFormat(String input) {
        return input == null ||
                input.isBlank() ||
                !input.matches(ValidationConstants.ID_REGEX);
    }
}



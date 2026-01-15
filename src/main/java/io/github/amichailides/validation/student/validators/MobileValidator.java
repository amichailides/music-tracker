package io.github.amichailides.validation.student.validators;

import io.github.amichailides.validation.ValidationConstants;

import java.util.Optional;

public class MobileValidator {
    public static Optional<String> isValid(String input) {
        if (input != null && !input.isBlank() && input.matches(ValidationConstants.PHONE_REGEX)){
            return Optional.of(input);
        }

        return Optional.empty();
    }
}

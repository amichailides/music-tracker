package io.github.amichailides.validation.validators;

import io.github.amichailides.validation.ValidationConstants;

import java.util.Optional;

public class NameValidator {
    private NameValidator() {};

    public static Optional<String> isValid(String name) {
        if (name != null && !name.isBlank() && name.matches(ValidationConstants.NAME_REGEX)){
            return Optional.of(name);
        }
        return Optional.empty();
    }
}

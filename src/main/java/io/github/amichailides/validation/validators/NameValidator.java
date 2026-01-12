package io.github.amichailides.validation.validators;

import io.github.amichailides.validation.ValidationConstants;

public class NameValidator {
    private NameValidator() {};

    public static boolean isValid(String name) {
        return name != null
                && !name.isBlank()
                && name.matches(ValidationConstants.NAME_REGEX);
    }
}

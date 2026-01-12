package io.github.amichailides.validation.validators;

import io.github.amichailides.validation.ValidationConstants;

public class EmailValidator {
    private EmailValidator () {};

    public static boolean isValid(String email) {
        return email != null
                && !email.isBlank()
                && email.matches(ValidationConstants.EMAIL_REGEX);
    }
}

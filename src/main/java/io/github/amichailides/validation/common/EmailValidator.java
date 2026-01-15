package io.github.amichailides.validation.common;

import io.github.amichailides.validation.ValidationConstants;

import java.util.Optional;

public class EmailValidator {
    private EmailValidator () {};

    public static Optional<String> isValid(String email) {
        if (email != null
                && !email.isBlank()
                && email.matches(ValidationConstants.EMAIL_REGEX)){
            return Optional.of(email);
        }

        return Optional.empty();
    }
}

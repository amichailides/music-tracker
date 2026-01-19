package io.github.amichailides.validation.common;

import java.util.Optional;

public class StringValidator {
    public static Optional<String> alwaysValid(String input) {
        return Optional.of(input);
    }
}

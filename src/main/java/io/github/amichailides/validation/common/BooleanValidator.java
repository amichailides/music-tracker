package io.github.amichailides.validation.common;

import java.util.Optional;

public class BooleanValidator {
    public static Optional<Boolean> isYesNo(String input) {
        if ("y".equalsIgnoreCase(input)) return Optional.of(true);
        if ("n".equalsIgnoreCase(input)) return Optional.of(false);
        return Optional.empty();
    }
}

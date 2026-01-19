package io.github.amichailides.validation.common;

import java.util.Optional;
import java.util.function.Function;

public class IntegerValidator {
    // Επιστρεφει μια συναρτηση που παιρνει String και βγαζει Optional<Integer>
    public static Function<String, Optional<Integer>> isBetween(int min, int max) {
        return input -> {
            try {
                // parse from String to int
                int value = Integer.parseInt(input);

                // validation
                if (value >= min && value <= max) {
                    return Optional.of(value);
                }
            } catch (NumberFormatException e) {
                // Αν δεν ειναι αριθμος, επιστρεφουμε αδειο Optional
            }
            return Optional.empty();
        };
    }
}

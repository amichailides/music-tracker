package io.github.amichailides.validation.student;

import io.github.amichailides.model.SkillLevel;

import java.util.Optional;

public class LevelValidator {
    private LevelValidator(){};

    public static Optional<String> isValid(String input) {
        if ( input == null || input.isBlank()){
            return Optional.empty();
        }
        try {
            SkillLevel.valueOf(input);
            return Optional.of(input);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}

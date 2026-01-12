package io.github.amichailides.validation.validators;

import io.github.amichailides.model.SkillLevel;

public class LevelValidator {
    private LevelValidator(){};

    public static boolean isValid(String input) {
        try {
            SkillLevel.valueOf(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }
}

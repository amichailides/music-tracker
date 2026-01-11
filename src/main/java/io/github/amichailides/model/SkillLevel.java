package io.github.amichailides.model;

public enum SkillLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public static boolean isValid(String value) {
        if (value == null || value.isBlank()) return false;
        for (SkillLevel level : SkillLevel.values()) {
            if (level.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}

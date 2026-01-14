package io.github.amichailides.validation.validators;

public class IdValidator {
    private IdValidator(){};

    public static boolean isValid (Long id) {
        return  id != null && id >= 0 ;
    }
}

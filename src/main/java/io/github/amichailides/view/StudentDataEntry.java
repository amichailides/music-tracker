package io.github.amichailides.view;

import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.ValidationConstants;
import io.github.amichailides.validation.sanitizers.*;
import io.github.amichailides.validation.validators.*;


public class StudentDataEntry {
    private final InputHandler inputHandler;
    public StudentDataEntry( InputHandler inputHandler) {

        this.inputHandler = inputHandler;
    }

    public  Long readStudentId() {
       // return inputHandler.readLong("Πληκτρολογειστε το ID του μαθητη: ");
        return inputHandler.readLong(
                "Πληκτρολογειστε το ID του μαθητη: ",
                IdSanitizer::clean,
                IdValidator::validateAndParse,
                ValidationConstants.INVALID_ID);
    }

    public String readStudentLastname() {
        return inputHandler.readString("Πληκτρολογειστε το επιθετο του μαθητη:",
                NameSanitizer::clean,
                NameValidator::isValid,
                ValidationConstants.INVALID_LAST_NAME
        );
    }

    public StudentCreateDTO collectStudentData() {
        String firstName = inputHandler.readValidated("Πληκτρολογηστε ονομα: ",
                StringSanitizer::clean,
                NameValidator::isValid,
                ValidationConstants.INVALID_FIRST_NAME
        );

        String lastName = inputHandler.readValidated("Πληκτρολογηστε επιθετο: ",
                StringSanitizer::clean,
                NameValidator::isValid,
                ValidationConstants.INVALID_LAST_NAME
        );

        String email = inputHandler.readValidated("Πληκτρολογηστε E-mail: ",
                StringSanitizer::clean,
                EmailValidator::isValid,
                ValidationConstants.INVALID_EMAIL
                // TODO: Να υλοποιηθεί μηχανισμός για συγκεκριμένα μηνύματα σφάλματος
                // (π.χ. "Λείπει το @", "Περιέχει κενά", κτλ) ανάλογα με την αποτυχία του Validator.
        );

        String mobile = inputHandler.readValidated("Πληκτρολογηστε αριθμο κινητου [+30]: ",
                MobileSanitizer::clean,
                MobileValidator::isValid,
                ValidationConstants.INVALID_PHONE
        );

        String level = inputHandler.readValidated("Πληκτρολογηστε επιπεδο [BEGINNER | INTERMEDIATE | ADVANCED]: ",
                LevelSanitizer::clean,
                LevelValidator::isValid,
                ValidationConstants.INVALID_LEVEL
        );



        return StudentCreateDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .mobile(mobile)
                .level(SkillLevel.valueOf(level))
                .build();

    }
}

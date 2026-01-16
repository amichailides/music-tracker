package io.github.amichailides.view;

import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.ValidationConstants;
import io.github.amichailides.validation.common.EmailSanitizer;
import io.github.amichailides.validation.common.EmailValidator;
import io.github.amichailides.validation.common.MobileSanitizer;
import io.github.amichailides.validation.common.MobileValidator;
import io.github.amichailides.validation.student.*;


public class StudentDataEntry {
    private final InputHandler inputHandler;
    public StudentDataEntry( InputHandler inputHandler) {

        this.inputHandler = inputHandler;
    }

    public Long readStudentId() {
       // return inputHandler.readLong("Πληκτρολογειστε το ID του μαθητη: ");
        return inputHandler.readLong(
                "Πληκτρολογειστε το ID του μαθητη: ",
                IdSanitizer::clean,
                IdValidator::validateAndParse,
                ValidationConstants.INVALID_ID);
    }

    public String readStudentFirstName() {
        return inputHandler.readString("Πληκτρολογειστε το ονομα του μαθητη:",
                NameSanitizer::clean,
                NameValidator::isValid,
                ValidationConstants.INVALID_FIRST_NAME
        );
    }

    public String readStudentLastname() {
        return inputHandler.readString("Πληκτρολογειστε το επιθετο του μαθητη: ",
                NameSanitizer::clean,
                NameValidator::isValid,
                ValidationConstants.INVALID_LAST_NAME
        );
    }

    public String readStudentEmail() {
        return inputHandler.readString("Πληκτρολογηστε E-mail: ",
                EmailSanitizer::clean,
                EmailValidator::isValid,
                ValidationConstants.INVALID_EMAIL
                // TODO: Να υλοποιηθεί μηχανισμός για συγκεκριμένα μηνύματα σφάλματος
                // (π.χ. "Λειπει το @", "Εχει κενα", κτλ.. αναλογα με την αποτυχια του Validator.
        );
    }

    public String readStudentMobile() {
        return inputHandler.readString("Πληκτρολογηστε αριθμο κινητου [+30]: ",
                MobileSanitizer::clean,
                MobileValidator::isValid,
                ValidationConstants.INVALID_PHONE
        );
    }

    public String readStudentLevel() {
        return inputHandler.readString("Πληκτρολογηστε επιπεδο [BEGINNER | INTERMEDIATE | ADVANCED]: ",
                LevelSanitizer::clean,
                LevelValidator::isValid,
                ValidationConstants.INVALID_LEVEL
        );
    }


    public StudentCreateDTO collectStudentData() {

        return StudentCreateDTO.builder()
                .firstName(readStudentFirstName())
                .lastName(readStudentLastname())
                .email(readStudentEmail())
                .mobile(readStudentMobile())
                .level(SkillLevel.valueOf(readStudentLevel()))
                .build();

    }
}

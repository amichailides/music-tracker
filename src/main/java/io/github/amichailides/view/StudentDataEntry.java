package io.github.amichailides.view;

import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.ValidationConstants;
import io.github.amichailides.validation.common.*;
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
                // TODO: Να το αλλαξω για συγκεκριμενα error μηνυματα
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

    public int readLessonUpdateChoice(int maxChoice) {

        String validOptions = (maxChoice == 3) ? "1, 2, 3 ή 0" : "1 ή 0";
        String errorMsg = "Μη εγκυρη επιλογη. Διαθεσιμες ενεργειες: " + validOptions;

        return inputHandler.readInt("Επιλεξτε ενεργεια: ",
                IntegerSanitizer::clean,
                IntegerValidator.isBetween(0, maxChoice),
                errorMsg
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

    public boolean confirmDeletion(String entityName) {
        String prompt = String.format("\u001B[33m[!] Είστε σίγουροι ότι θέλετε να διαγράψετε το %s; (y/n): \u001B[0m", entityName);

        return inputHandler.readBoolean(prompt,
                StringSanitizer::clean,
                BooleanValidator::isYesNo,
                ValidationConstants.INVALID_YES_NO_INPUT
        );
    }
}

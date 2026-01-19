package io.github.amichailides.view;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.ValidationConstants;
import io.github.amichailides.validation.common.IntegerValidator;
import io.github.amichailides.validation.common.StringSanitizer;
import io.github.amichailides.validation.student.IdSanitizer;
import io.github.amichailides.validation.student.IdValidator;

import java.time.LocalDate;
import java.util.Optional;


public class LessonDataEntry {
    private final InputHandler inputHandler;

    public LessonDataEntry(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public Long readLessonId (){
        return inputHandler.readLong("Εισαγετε ID μαθηματος: ",
                IdSanitizer::clean,
                IdValidator::validateAndParse,
                ValidationConstants.INVALID_ID
        );
    }

    public String readLessonComments() {
        return inputHandler.readValidated(
                "Σχολια μαθηματος [Enter για skip]: ",
                StringSanitizer::clean,
                homeworkText -> Optional.of(homeworkText),
                ""
        );
    }

    public String readLessonHomework() {
        return inputHandler.readValidated(
                "Εργασια για το σπιτι [Enter για skip]: ",
                StringSanitizer::clean,
                homeworkText -> Optional.of(homeworkText),
                ""
        );
    }

    public LocalDate readLessonDate() {
        return LocalDate.now();
    }

    public LessonCreateDTO collectLessonData() {

        return LessonCreateDTO.builder()
                .date(readLessonDate())
                .comments(defaultIfEmpty(readLessonComments()))
                .homework(defaultIfEmpty(readLessonHomework()))
                .build();
    }

    public int readLessonNumber(int maxCount) {
        return inputHandler.readInt(
                "Επιλέξτε τον αριθμό (#) του μαθήματος: ",
                StringSanitizer::clean,
                IntegerValidator.isBetween(1, maxCount),
                "Λάθος επιλογή! Παρακαλώ δώστε έναν αριθμό από 1 έως " + maxCount
        );
    }

    private String defaultIfEmpty(String value) {
        return value.isEmpty() ? "-" : value;
    }
}

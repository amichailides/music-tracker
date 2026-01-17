package io.github.amichailides.view;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.common.StringSanitizer;

import java.time.LocalDate;
import java.util.Optional;


public class LessonDataEntry {
    private final InputHandler inputHandler;

    public LessonDataEntry(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
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

    private String defaultIfEmpty(String value) {
        return value.isEmpty() ? "-" : value;
    }
}

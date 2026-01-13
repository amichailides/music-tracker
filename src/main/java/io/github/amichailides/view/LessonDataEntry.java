package io.github.amichailides.view;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.sanitizers.StringSanitizer;
import java.time.LocalDate;


public class LessonDataEntry {
    private final InputHandler inputHandler;

    public LessonDataEntry(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

     public LessonCreateDTO collectLessonData() {
         LocalDate date = LocalDate.now();

         String rawComments = inputHandler.readValidated(
                 "Σχολια μαθηματος [Enter για skip]: ",
                 StringSanitizer::clean,
                 commentsText -> true, // Δεν χρειαζομαι validation στα comments
                 "" // ουτε error message, θα περασει αερα !
         );

         String comments = rawComments.isEmpty() ? "-" : rawComments;

         String rawHomework = inputHandler.readValidated(
                 "Εργασία για το σπίτι [Enter για skip]: ",
                 StringSanitizer::clean,
                 homeworkText -> true,
                 ""
         );

         String homework = rawHomework.isEmpty() ? "-" : rawHomework;

         return LessonCreateDTO.builder()
                 .date(date)
                 .comments(comments)
                 .homework(homework)
                 .build();
     }
}

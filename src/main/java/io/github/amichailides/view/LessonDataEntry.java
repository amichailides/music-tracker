package io.github.amichailides.view;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.sanitizers.NameSanitizer;

import java.time.LocalDate;
import java.util.Scanner;

public class LessonDataEntry {
    private final InputHandler inputHandler;
    private Scanner scanner;

    public LessonDataEntry(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.scanner = scanner;
    }
    /*
    public LessonCreateDTO collectLessonData() {
        System.out.println("--- Εισαγωγη Στοιχειων Μαθηματος ---");
        LocalDate date = LocalDate.now();
        System.out.print("Σχολια: ");
        String comments = scanner.nextLine();
        System.out.print("Ασκησεις για το σπιτι: ");
        String homework = scanner.nextLine();

        return LessonCreateDTO.builder()
                .date(date)
                .comments(comments)
                .homework(homework)
                .build();
    }
    */
     public LessonCreateDTO collectLessonData() {
         LocalDate date = LocalDate.now();

         String rawComments = inputHandler.readValidated(
                 "Σχολια μαθηματος [Enter για skip]: ",
                 NameSanitizer::clean,
                 commentsText -> true, // Δεν χρειαζομαι validation στα comments
                 "" // ουτε error message, θα περασει αερα !
         );

         String comments = rawComments.isEmpty() ? "-" : rawComments;

         String rawHomework = inputHandler.readValidated(
                 "Εργασία για το σπίτι [Enter για skip]: ",
                 NameSanitizer::clean,
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

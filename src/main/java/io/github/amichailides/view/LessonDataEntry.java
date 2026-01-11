package io.github.amichailides.view;

import io.github.amichailides.dto.LessonCreateDTO;

import java.time.LocalDate;
import java.util.Scanner;
// TODO: Refactor static method to Instance method for DI.
// 1. Remove static keyword.
// 2. Add private final Scanner scanner field.
// 3. Create a constructor that accepts Scanner.
public class LessonDataEntry {
    private Scanner scanner;

    public LessonDataEntry(Scanner scanner) {
        this.scanner = scanner;
    }

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
}

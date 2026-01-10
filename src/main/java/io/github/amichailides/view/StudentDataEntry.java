package io.github.amichailides.view;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.model.SkillLevel;

import java.time.LocalDate;
import java.util.Scanner;


public class StudentDataEntry {
    private final Scanner scanner;

    public StudentDataEntry(Scanner scanner) {
        this.scanner = scanner;
    }

    public static StudentCreateDTO collectStudentData(Scanner scanner){
        System.out.print("Ονομα: ");
        String firstName = scanner.nextLine();
        System.out.print("Επωνυμο: ");
        String lastName = scanner.nextLine();
        System.out.print("Διευθυνση Email: ");
        String mail = scanner.nextLine();
        System.out.print("Αριθμος κινητου: ");
        String mobile = scanner.nextLine();
        System.out.println("Επιπεδο: ");
        System.out.print("(BEGINNER | INTERMEDIATE | ADVANCED)");
        SkillLevel level = SkillLevel.valueOf(scanner.nextLine().toUpperCase());
        return StudentCreateDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(mail)
                .mobile(mobile)
                .level(level)
                .build();
    }



    public static Long readStudentId(Scanner scanner) {
        System.out.print("Εισαγετε το id του Μαθητη: ");
        return Long.parseLong(scanner.nextLine());
    }
}

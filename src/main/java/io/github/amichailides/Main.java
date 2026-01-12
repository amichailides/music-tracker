package io.github.amichailides;

import io.github.amichailides.controller.Controller;
import io.github.amichailides.dto.*;
import io.github.amichailides.repository.LessonRepository;
import io.github.amichailides.repository.SqlLessonRepository;
import io.github.amichailides.repository.StudentRepository;
import io.github.amichailides.repository.SqlStudentRepository;
import io.github.amichailides.service.Service;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        //IStudentRepository repository = new InMemoryRepository();
        StudentRepository studentRepo = new SqlStudentRepository();
        LessonRepository lessonRepo = new SqlLessonRepository();
        Service service = new Service(studentRepo, lessonRepo);
        StudentPrinter printer = new StudentPrinter();
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(scanner);
        StudentDataEntry studentEntry = new StudentDataEntry(inputHandler);
        LessonDataEntry lessonEntry = new LessonDataEntry(scanner);

        Controller controller = new Controller(
                service,
                inputHandler,
                printer,
                lessonEntry,
                studentEntry);

        boolean isRunning = true;

        do {
            try {
                printMenu();
                int inputAction = Integer.parseInt(scanner.nextLine());
                switch (inputAction) {
                    case 1 -> controller.registerStudent();
                    case 2 -> controller.displayAllStudents();
                    case 3 -> controller.addLessonToStudent();
                    case 4 -> controller.displayStudentLessons();
                    case 5 -> controller.deleteStudent();
                    case 6 -> controller.updateStudent();
                    case 0 -> isRunning = false;
                }

            }catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Σφαλμα -> " + e.getMessage());
            }
        } while (isRunning);


    }

    private static void printMenu() {
        System.out.println("\n=== MUSIC TRACKER MENU ===");
        System.out.println("1. Εγγραφη νεου μαθητη");
        System.out.println("2. Προβολη ολων των μαθητων");
        System.out.println("3. Προσθηκη μαθηματος σε μαθητη");
        System.out.println("4. Προβολη ιστορικου μαθηματων");
        System.out.println("5. Διαγραφη Μαθητη");
        System.out.println("6. Ενημερωση στοιχειων μαθητη (Update)");
        System.out.println("0. Έξοδος");
        System.out.print("Επιλέξτε ενέργεια: ");
    }

}
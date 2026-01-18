package io.github.amichailides;

import io.github.amichailides.controller.Controller;
import io.github.amichailides.dto.*;
import io.github.amichailides.repository.*;
import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        } catch (Exception ignored) {}
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
        Scanner scanner = new Scanner(System.in);

        Controller controller = AppFactory.createController(scanner);

        boolean isRunning = true;
        do {
            try {
                printMenu();
                int inputAction = Integer.parseInt(scanner.nextLine());
                switch (inputAction) {
                    case 1 -> controller.registerStudent();
                    case 2 -> controller.displayAllStudents();
                    case 3 -> controller.addLessonToStudent();
                    case 4 -> controller.handleStudentProfile();
                    case 5 -> controller.deleteStudent();
                    case 7 -> controller.displayMatchingStudents();
                    case 8 -> controller.deleteLesson();
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
        System.out.println("7. Αναζήτηση μαθητή με επώνυμο");
        System.out.println("8. Διαγραφη μαθηματος");
        System.out.println("0. Έξοδος");
        System.out.print("Επιλέξτε ενέργεια: ");
    }

}
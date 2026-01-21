package io.github.amichailides;

import io.github.amichailides.controller.Controller;
import io.github.amichailides.dto.*;
import io.github.amichailides.repository.*;
import io.github.amichailides.view.StudentPrinter;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
        Scanner scanner = new Scanner(System.in);
        StudentPrinter printer = new StudentPrinter();

        Controller controller = AppFactory.createController(scanner, printer);

        boolean isRunning = true;
        do {
            try {
                printMenu();

                int inputAction = Integer.parseInt(scanner.nextLine());

                switch (inputAction) {
                    case 1 -> controller.registerStudent();
                    case 2 -> controller.displayAllStudents();
                    case 3 -> controller.displayMatchingStudents(); // Αναζήτηση (πρώην 7)
                    case 4 -> controller.addLessonToStudent();
                    case 5 -> controller.handleStudentProfile();    // Καρτέλα & Διαχείριση
                    case 6 -> controller.deleteStudent();           // Διαγραφή (τελευταία επιλογή)
                    case 0 -> isRunning = false;
                    default -> printer.printError("Μη έγκυρη επιλογή. Παρακαλώ επιλέξτε από 0 έως 6.");
                }

            } catch (NumberFormatException e) {
                printer.printError("Παρακαλώ εισάγετε μόνο αριθμούς.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                printer.printError(e.getMessage());
            }
        } while (isRunning);


    }

    private static void printMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("      MUSIC TRACKER MENU");
        System.out.println("=".repeat(30));
        System.out.println("1. Εγγραφή νέου μαθητή");
        System.out.println("2. Προβολή όλων των μαθητών");
        System.out.println("3. Αναζήτηση μαθητή (Επώνυμο)");
        System.out.println("4. Προσθήκη μαθήματος");
        System.out.println("5. Καρτέλα Μαθητή & Διαχείριση");
        System.out.println("6. Διαγραφή Μαθητή");
        System.out.println("-".repeat(30));
        System.out.println("0. Έξοδος");
        System.out.println("=".repeat(30));
        System.out.print("Επιλογή: ");
    }

}
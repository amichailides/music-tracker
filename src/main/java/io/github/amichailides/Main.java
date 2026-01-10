package io.github.amichailides;

import io.github.amichailides.dto.*;

import io.github.amichailides.repository.LessonRepository;
import io.github.amichailides.repository.SqlLessonRepository;
import io.github.amichailides.repository.StudentRepository;
import io.github.amichailides.repository.SqlStudentRepository;
import io.github.amichailides.service.Service;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //IStudentRepository repository = new InMemoryRepository();
        StudentRepository studentRepo = new SqlStudentRepository();
        LessonRepository lessonRepo = new SqlLessonRepository();
        Service service = new Service(studentRepo, lessonRepo);
        StudentPrinter printer = new StudentPrinter();
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;

        do {
            try {
                printMenu();
                int inputAction = Integer.parseInt(scanner.nextLine());
                switch (inputAction) {
                    case 1: {
                        StudentCreateDTO studentDTO = StudentDataEntry.collectStudentData(scanner);
                        service.registerStudent(studentDTO);
                        System.out.printf("Η εγγραφη του %s ολοκληρωθηκε με επιτυχια %n",
                                studentDTO.getFirstName() + " " + studentDTO.getLastName());
                        break;
                    }
                    case 2: {
                        StudentListDTO allStudents = service.prepareStudentsForPrint(studentRepo.findAll());
                        printer.printAllStudents(allStudents);
                        break;
                    }
                    case 3: {
                        // TODO: Switch to instance call after DI refactor (e.g., lessonEntry.collectLessonData())
                        // FIXME: Check for Scanner buffer issue (nextLine after nextInt)
                        LessonCreateDTO lessonDTO = LessonDataEntry.collectLessonData(scanner);
                        Long studentId = StudentDataEntry.readStudentId(scanner);
                        service.addLesson(studentId, lessonDTO);
                        System.out.println("Η εγγραφη του μαθηματος ολοκληρωθηκε με επιτυχια");
                        break;
                    }
                    case 4: {
                        try {
                            Long studentId = StudentDataEntry.readStudentId(scanner);
                            service.getStudentProfile(studentId).ifPresentOrElse(
                                    profile -> printer.printFullProfile(profile),
                                    () -> System.out.printf("Σφαλμα: ο μαθητης με id: %d δεν βρεθηκε.%n", studentId)
                            );
                        } catch (NumberFormatException e) {
                            System.out.println("Ακυρο id. Παρακαλω προσπαθειστε ξανα.");
                        }

                        break;
                    }
                    case 5: {
                        System.out.println("--- Διαγραφη Μαθητη ---");
                        try {
                            Long studentId = StudentDataEntry.readStudentId(scanner);
                            service.deleteStudent(studentId);
                            System.out.println("Επιτυχια: Ο μαθητης με ID " + studentId + " διαγράφηκε οριστικά.");
                        } catch (NoSuchElementException | IllegalArgumentException e) {
                            System.out.println("Σφαλμα: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Απροσμενο σφαλμα: " + e.getMessage());
                        }

                        break;
                    }
                    case 6: {
                        System.out.println("--- Ενημέρωση Μαθητή ---");
                        Long studentId = StudentDataEntry.readStudentId(scanner);
                    }
                    case 0 : isRunning = false;

                }

            }catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Σφαλμα -> " + e.getMessage());
            }
        } while (isRunning);


    }

    private static void printMenu() {
        System.out.println("\n=== MUSIC TRACKER MENU ===");
        System.out.println("1. Εγγραφή νέου μαθητή");
        System.out.println("2. Προβολή όλων των μαθητών");
        System.out.println("3. Προσθήκη μαθήματος σε μαθητή");
        System.out.println("4. Προβολή ιστορικού μαθημάτων");
        System.out.println("5. Διαγραφη Μαθητη");
        System.out.println("0. Έξοδος");
        System.out.print("Επιλέξτε ενέργεια: ");
    }
}
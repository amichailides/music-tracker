package io.github.amichailides;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;
import io.github.amichailides.repository.LessonRepository;
import io.github.amichailides.repository.SqlLessonRepository;
import io.github.amichailides.repository.StudentRepository;
import io.github.amichailides.repository.SqlStudentRepository;
import io.github.amichailides.service.Service;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;


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
                        System.out.println("--- Ενημερωση Μαθητη ---");
                        Long studentId = StudentDataEntry.readStudentId(scanner);
                        try {
                            StudentUpdateDTO currentData = service.getStudentForUpdate(studentId);

                            System.out.println("Εισαγετε νεα δεδομενα " +
                                    "(Πατηστε enter για να κρατησετε την τρεχουσα τιμη ");

                            System.out.printf("Ονομα [%s]: ", currentData.getFirstName());
                            String newFirstName = scanner.nextLine().trim();

                            System.out.printf("Επωνυμο [%s]: ", currentData.getLastName());
                            String newLastName = scanner.nextLine().trim();

                            System.out.printf("Email [%s]: ", currentData.getEmail());
                            String newEmail = scanner.nextLine().trim();

                            System.out.printf("Level (BEGINNER, INTERMEDIATE, ADVANCED) [%s]: ", currentData.getLevel());
                            String newLevel = scanner.nextLine().trim();

                            if (!newLevel.isBlank() && !SkillLevel.isValid(newLevel)) {
                                System.out.printf("To level %s δεν ειναι εγκυρο", newLevel);
                                break;
                            }

                            StudentUpdateDTO changedDTO = StudentUpdateDTO.builder()
                                    .firstName(newFirstName)
                                    .lastName(newLastName)
                                    .email(newEmail)
                                    .level(newLevel)
                                    .build();

                            service.updateStudent(studentId, changedDTO);

                        } catch (RuntimeException e) {
                            System.out.println("Σφαλμα: " + e.getMessage());
                        }
                        break;
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
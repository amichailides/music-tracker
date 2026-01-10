package io.github.amichailides;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.dto.LessonListDTO;
import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.dto.StudentListDTO;

import io.github.amichailides.repository.StudentRepository;
import io.github.amichailides.repository.SqlStudentRepository;
import io.github.amichailides.service.Service;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //IStudentRepository repository = new InMemoryRepository();
        StudentRepository repository = new SqlStudentRepository();
        Service service = new Service(repository);
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
                        StudentListDTO allStudents = service.prepareStudentsForPrint(repository.findAll());
                        printer.printAllStudents(allStudents);
                        break;
                    }
                    case 3: {
                        /*
                        if (repository.findAll().isEmpty()){
                            throw new IllegalStateException("Δεν υπαρχουν ενεργοι μαθητες.");
                        }
                        */
                        // TODO: Switch to instance call after DI refactor (e.g., lessonEntry.collectLessonData())
                        // FIXME: Check for Scanner buffer issue (nextLine after nextInt)
                        LessonCreateDTO lessonDTO = LessonDataEntry.collectLessonData(scanner);
                        Long studentId = StudentDataEntry.readStudentId(scanner);
                        service.addLesson(studentId, lessonDTO);
                        System.out.println("Η εγγραφη του μαθηματος ολοκληρωθηκε με επιτυχια");
                        break;
                    }
                    case 4: {

                        Long studentId = StudentDataEntry.readStudentId(scanner);
                        LessonListDTO allStudentLessons = service.prepareLessonsForPrint(studentId);
                        printer.printStudentLessons(allStudentLessons);
                        break;
                    }
                    case 5: {
                        System.out.println("--- Διαγραφη Μαθητη ---");
                        System.out.print("Εισαγετε το ID του μαθητη που θελετε να διαγραψετε: ");

                        Long id = Long.parseLong(scanner.nextLine());

                        service.deleteStudent(id);


                        System.out.println("Επιτυχια: Ο μαθητης με ID " + id + " διαγράφηκε οριστικά.");
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
        System.out.println("1. Εγγραφή νέου μαθητή");
        System.out.println("2. Προβολή όλων των μαθητών");
        System.out.println("3. Προσθήκη μαθήματος σε μαθητή");
        System.out.println("4. Προβολή ιστορικού μαθημάτων");
        System.out.println("5. Διαγραφη Μαθητη");
        System.out.println("0. Έξοδος");
        System.out.print("Επιλέξτε ενέργεια: ");
    }
}
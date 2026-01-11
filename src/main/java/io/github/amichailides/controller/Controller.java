package io.github.amichailides.controller;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.dto.StudentListDTO;
import io.github.amichailides.dto.StudentUpdateDTO;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;
import io.github.amichailides.service.Service;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Controller {
    private final Service service;
    private final Scanner scanner;
    private final StudentPrinter printer;
    private final LessonDataEntry lessonEntry;
    private final StudentDataEntry studentEntry;


    public Controller(Service service, Scanner scanner, StudentPrinter printer, LessonDataEntry lessonEntry, StudentDataEntry studentEntry) {
        this.service = service;
        this.scanner = scanner;
        this.printer = printer;
        this.lessonEntry = lessonEntry;
        this.studentEntry = studentEntry;
    }


    public void registerStudent () {
        StudentCreateDTO studentDTO = studentEntry.collectStudentData();
        Student s = service.registerStudent(studentDTO);
        System.out.printf("Η εγγραφη του %s %s με id: %d ολοκληρωθηκε με επιτυχια %n",
                s.getFirstName(), s.getLastName(), s.getId());
    }

    public void displayAllStudents () {
        StudentListDTO allStudents = service.prepareStudentsForPrint();
        printer.printAllStudents(allStudents);
    }

    public void addLessonToStudent () {
        LessonCreateDTO lessonDTO = lessonEntry.collectLessonData();
        Long studentId = studentEntry.readStudentId();
        service.addLesson(studentId, lessonDTO);
        System.out.println("Η εγγραφη του μαθηματος ολοκληρωθηκε με επιτυχια");

    }

    public void displayStudentLessons () {
        try {
            Long studentId = studentEntry.readStudentId();
            service.getStudentProfile(studentId).ifPresentOrElse(
                    profile -> printer.printFullProfile(profile),
                    () -> System.out.printf("Σφαλμα: ο μαθητης με id: %d δεν βρεθηκε.%n", studentId)
            );
        } catch (NumberFormatException e) {
            System.out.println("Ακυρο id. Παρακαλω προσπαθειστε ξανα.");
        }
    }

    public void deleteStudent () {
        try {
            Long studentId = studentEntry.readStudentId();
            service.deleteStudent(studentId);
            System.out.println("Επιτυχια: Ο μαθητης με ID " + studentId + " διαγράφηκε οριστικά.");
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println("Σφαλμα: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Απροσμενο σφαλμα: " + e.getMessage());
        }
    }

    public void updateStudent () {
        Long studentId = studentEntry.readStudentId();
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

            String newLevel;
            while (true){
                System.out.printf("Level (BEGINNER, INTERMEDIATE, ADVANCED) [%s]: ", currentData.getLevel());
                newLevel = scanner.nextLine().trim().toUpperCase();

                if (newLevel.isBlank() || SkillLevel.isValid(newLevel)) {
                    break;
                }
                System.out.printf("To level %s δεν ειναι εγκυρο", newLevel);
            }


            StudentUpdateDTO changedDTO = StudentUpdateDTO.builder()
                    .firstName(newFirstName)
                    .lastName(newLastName)
                    .email(newEmail)
                    .level(newLevel)
                    .build();

            studentId = service.updateStudent(studentId, changedDTO);

            System.out.printf("Ο μαθητης με id: %d ενημερωθηκε επιτυχως", studentId);

        } catch (RuntimeException e) {
            System.out.println("Σφαλμα: " + e.getMessage());
        }
    }

}


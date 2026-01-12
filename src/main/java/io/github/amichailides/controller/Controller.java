package io.github.amichailides.controller;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.dto.StudentListDTO;
import io.github.amichailides.dto.StudentUpdateDTO;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;
import io.github.amichailides.service.Service;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.sanitizers.LevelSanitizer;
import io.github.amichailides.validation.sanitizers.NameSanitizer;
import io.github.amichailides.validation.validators.EmailValidator;
import io.github.amichailides.validation.validators.LevelValidator;
import io.github.amichailides.validation.validators.NameValidator;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class Controller {
    private final Service service;
    private final InputHandler inputHandler;
    private final StudentPrinter printer;
    private final LessonDataEntry lessonEntry;
    private final StudentDataEntry studentEntry;


    public Controller(Service service, InputHandler inputHandler, StudentPrinter printer, LessonDataEntry lessonEntry, StudentDataEntry studentEntry) {
        this.service = service;
        this.inputHandler = inputHandler;
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

    public void updateStudent() {
        try {
            Long studentId = studentEntry.readStudentId();
            StudentUpdateDTO currentData = service.getStudentForUpdate(studentId);

            System.out.println("--- Ενημέρωση Στοιχείων ---");

            String newFirstName = readOptional("Όνομα", currentData.getFirstName(), NameSanitizer::clean, NameValidator::isValid);
            String newLastName = readOptional("Επώνυμο", currentData.getLastName(), NameSanitizer::clean, NameValidator::isValid);
            String newEmail = readOptional("Email", currentData.getEmail(), NameSanitizer::clean, EmailValidator::isValid);
            // TODO: Να προστεθεί η ανάγνωση του mobile (readOptional) και η εισαγωγή του στον Builder παρακάτω
            String newLevel = readOptional("Level", currentData.getLevel(), LevelSanitizer::clean, LevelValidator::isValid);





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

    private String readOptional(
            String label,
            String currentValue,
            Function<String, String> sanitizer,
            Predicate<String> validator) {

        String input = inputHandler.readValidated(
                String.format("%s [%s]: ", label, currentValue),
                sanitizer,
                val -> val.isBlank() || validator.test(val),
                "Λάθος μορφή! Ξαναπροσπάθησε ή πάτα Enter για παράκαμψη."
        );
        return input.isBlank() ? currentValue : input;
    }

}


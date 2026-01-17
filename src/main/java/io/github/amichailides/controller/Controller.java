package io.github.amichailides.controller;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.Student;
import io.github.amichailides.service.Service;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.student.LevelSanitizer;
import io.github.amichailides.validation.common.StringSanitizer;
import io.github.amichailides.validation.common.EmailValidator;
import io.github.amichailides.validation.student.LevelValidator;
import io.github.amichailides.validation.common.MobileValidator;
import io.github.amichailides.validation.student.NameValidator;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Builder
@AllArgsConstructor
public class Controller {
    private final Service service;
    private final InputHandler inputHandler;
    private final StudentPrinter printer;
    private final LessonDataEntry lessonEntry;
    private final StudentDataEntry studentEntry;


    public void registerStudent () {
        StudentCreateDTO studentDTO = studentEntry.collectStudentData();
        Student s = service.registerStudent(studentDTO);
        System.out.printf("Η εγγραφη του %s %s με id: %d ολοκληρωθηκε με επιτυχια %n",
                s.getFirstName(), s.getLastName(), s.getId());
    }

    public void displayAllStudents () {
        StudentListDTO allStudents = service.prepareStudentsForPrint();
        printer.printStudentTable(allStudents.getStudents(), "Ολοι μαθητες");
    }

    public void addLessonToStudent () {
        Long studentId = studentEntry.readStudentId();

        LessonCreateDTO lessonDTO = lessonEntry.collectLessonData();
        service.addLesson(studentId, lessonDTO);
        printer.printSuccess("Η εγγραφη του μαθηματος ολοκληρωθηκε με επιτυχια");

    }

    public void displayStudentLessons () {
        try {
            Long studentId = studentEntry.readStudentId();
            service.getStudentProfile(studentId).ifPresentOrElse(
                    profile -> printer.printFullProfile(profile),
                    () -> printer.printError("Ο μαθητης με id: " + studentId + " δεν βρεθηκε.")
            );
        } catch (NumberFormatException e) {
            printer.printError("Ακυρο ID. Παρακαλω εισαγετε μονο αριθμους.");
        }
    }

    public void deleteStudent () {
        try {
            Long studentId = studentEntry.readStudentId();
            service.deleteStudent(studentId);
            printer.printSuccess("Ο μαθητης με ID " + studentId + " διαγραφηκε οριστικα.");
        } catch (NoSuchElementException | IllegalArgumentException e) {
            printer.printError(e.getMessage());
        } catch (Exception e) {
            printer.printError("Απροσμενο σφαλμα: " + e.getMessage());
        }
    }

    public void updateStudent() {
        try {
            Long studentId = studentEntry.readStudentId();
            StudentUpdateDTO currentData = service.getStudentForUpdate(studentId);

            printer.printUpdateHeader();

            String newFirstName = readOptional("Όνομα", currentData.getFirstName(), StringSanitizer::clean, NameValidator::isValid);
            String newLastName = readOptional("Επώνυμο", currentData.getLastName(), StringSanitizer::clean, NameValidator::isValid);
            String newEmail = readOptional("Email", currentData.getEmail(), StringSanitizer::clean, EmailValidator::isValid);
            String newMobile = readOptional("Κινητο: ", currentData.getMobile(), StringSanitizer::clean, MobileValidator::isValid);
            String newLevel = readOptional("Level", currentData.getLevel(), LevelSanitizer::clean, LevelValidator::isValid);





            StudentUpdateDTO changedDTO = StudentUpdateDTO.builder()
                    .firstName(newFirstName)
                    .lastName(newLastName)
                    .email(newEmail)
                    .mobile(newMobile)
                    .level(newLevel)
                    .build();

            studentId = service.updateStudent(studentId, changedDTO);

            printer.printSuccess("Ο μαθητης με id: %d ενημερωθηκε επιτυχως" + studentId);

        } catch (RuntimeException e) {
            printer.printError(e.getMessage());
        }
    }


    private String readOptional(
            String label,
            String currentValue,
            Function<String, String> sanitizer,
            Function<String, Optional<String>> validator) {

        // αν δωσει κενο "" η readValidated βλεπει το empty() και εμφανιζει "λαθος ξαναπροσπαθησε"
        // δινουμε έναν τοπικο validator που επιτρεπει τα κενα
        Function<String, Optional<String>> optionalValidator = input -> {
            if (input.isBlank()) {
                return Optional.of(input); // if blank, περνα το
            }
            return validator.apply(input); // Αλλιως καλεσε τον κανονικο validator (κοιτα κατω!)
        };

        String input = inputHandler.readValidated(
                String.format("%s [%s]: ", label, currentValue),
                sanitizer,
                optionalValidator,
                "Λάθος μορφή! Ξαναπροσπάθησε ή πάτα Enter για παράκαμψη."
        );
        return input.isBlank() ? currentValue : input;
    }

    public void displayMatchingStudents(){
        String rawLastName = studentEntry.readStudentLastname();
        StudentListDTO students = service.findStudentsByLastName(rawLastName);
        printer.printStudentTable(students.getStudents(), "Αναζητηση: " + rawLastName);
    }

    public void createLesson(){
        LessonCreateDTO lessonDTO = lessonEntry.collectLessonData();

    }

}


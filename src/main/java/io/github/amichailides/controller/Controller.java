package io.github.amichailides.controller;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.Student;
import io.github.amichailides.service.Service;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.validation.common.*;
import io.github.amichailides.validation.student.LevelSanitizer;
import io.github.amichailides.validation.student.LevelValidator;
import io.github.amichailides.validation.student.NameValidator;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
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
        printer.printStudentTable(allStudents.getStudents(), "Μητρώο Μαθητών");
    }

    public void addLessonToStudent () {
        Long studentId = studentEntry.readStudentId();

        LessonCreateDTO lessonDTO = lessonEntry.collectLessonData();
        service.addLesson(studentId, lessonDTO);
        printer.printSuccess("Η εγγραφη του μαθηματος ολοκληρωθηκε με επιτυχια");

    }

    public void deleteStudent () {
        try {

            Long studentId = studentEntry.readStudentId();
            service.deleteStudent(studentId);
            printer.printSuccess("Ο μαθητης με ID " + studentId + " διαγραφηκε.");

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printer.printError(e.getMessage());
        } catch (Exception e) {
            printer.printError("Απροσμενο σφαλμα: " + e.getMessage());
        }
    }

    public void updateStudent(Long studentId) {
        try {
            StudentUpdateDTO currentData = service.getStudentForUpdate(studentId);

            printer.printStudentUpdateHeader();

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

            printer.printSuccess("Ο μαθητης με id: " + studentId + " ενημερωθηκε επιτυχως" );

        } catch (RuntimeException e) {
            printer.printError(e.getMessage());
        }
    }

    public void displayMatchingStudents(){
        String rawLastName = studentEntry.readStudentLastname();
        StudentListDTO students = service.findStudentsByLastName(rawLastName);
        printer.printStudentTable(students.getStudents(), "Αποτελεσματα Αναζητησης: " + rawLastName);
    }

    public void handleStudentProfile() {
        Long studentId = studentEntry.readStudentId();

        while (true) {
            Optional<StudentProfileDTO> profileOpt = service.getStudentProfile(studentId);

            if (profileOpt.isEmpty()){
                printer.printError("Ο μαθητης δεν βρεθηκε");
                return; // returns to main menu
            }

            StudentProfileDTO profile = profileOpt.get();
            printer.printFullProfile(profile);

            boolean hasLessons = !profile.getLessons().isEmpty();
            int maxChoice = hasLessons ? 3 : 1;
            printer.printPostDisplayActions(hasLessons);
            int userChoice = studentEntry.readLessonUpdateChoice(maxChoice);



           if (!handleUserAction(userChoice, profile, studentId)) {
               return;
           }
        }

    }

    public void updateLesson(StudentProfileDTO profile){

        try {
            List<LessonPrintDTO> lessons = profile.getLessons();
            int choice = lessonEntry.readLessonNumber(lessons.size());
            //παιρνω id απο το # της λιστας που εκτυπωσα πριν, -1 γιατι ξεκιναει απο το 1 στο print
            Long lessonId = profile.getLessons().get(choice -1).getId();

            LessonUpdateDTO currentData = service.getLessonForUpdate(lessonId);

            printer.printLessonUpdateHeader();

            String newDate = readOptional("Ημερομηνια (YYYY-MM-DD)", currentData.getDate(), StringSanitizer::clean, NameValidator::isValid);
            String newComments = readOptional("Σχολια", currentData.getComments(), StringSanitizer::clean,StringValidator::alwaysValid);
            String newHomework = readOptional("Homework", currentData.getHomework(), StringSanitizer::clean,StringValidator::alwaysValid);

            LessonUpdateDTO changedDTO = LessonUpdateDTO.builder()
                    .date(newDate)
                    .comments(newComments)
                    .homework(newHomework)
                    .build();

            lessonId = service.updateLesson(lessonId, changedDTO);
            printer.printSuccess("Το μαθημα με ID: " + lessonId + " ενημερωθηκε επιτυχως");
        } catch (RuntimeException e) {
            printer.printError(e.getMessage());
        }

    }

    public void deleteLesson(StudentProfileDTO profile) {
        try {
            Long studentId = profile.getStudentDetails().getId();
            List<LessonPrintDTO> lessons = profile.getLessons();
            if (lessons.isEmpty()) {
                printer.printError(" [!] Δεν βρέθηκε ιστορικό μαθημάτων για τον συγκεκριμένο μαθητή.");
                return;
            }
            int choice = lessonEntry.readLessonNumber(lessons.size());
            Long lessonId = lessons.get(choice -1).getId();

            if (studentEntry.confirmDeletion("Μαθημα")) {
                service.deleteLesson(studentId,lessonId);
                printer.printSuccess("Το μαθημα διαγραφηκε επιτυχως.");
            } else {
                printer.printError("Ηδ διαγραφη ακυρωθηκε απο τον χρηστη");
            }

        } catch (RuntimeException e) {
            printer.printError("Σφαλμα κατα την διαγραφη: " + e.getMessage());
        }
    }


    private boolean handleUserAction (int choice, StudentProfileDTO profile, Long studentId){
        switch (choice) {
            case 1 -> updateStudent(studentId);
            case 2 -> updateLesson(profile);
            case 3 -> deleteLesson(profile);
            case 0 -> {return false;}
            default -> {return true;}
        }
        return true;
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

}


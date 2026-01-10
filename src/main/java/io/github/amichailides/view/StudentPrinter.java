package io.github.amichailides.view;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.Lesson;

import java.util.List;


public class StudentPrinter {

    public void printAllStudents(StudentListDTO dto) {
        if (dto == null || dto.getStudents() == null || dto.getStudents().isEmpty()) {
            System.out.println("Δεν βρέθηκαν μαθητές για εκτύπωση.");
            return;
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.printf("%-5s | %-25s | %-25s | %-10s%n", "ID", "ΟΝΟΜΑΤΕΠΩΝΥΜΟ", "EMAIL", "LEVEL");
        System.out.println("----------------------------------------------------------------------------");

        for (StudentPrintDTO s : dto.getStudents()) {
            System.out.printf("%-5s | %-25s | %-25s | %-10s%n",
                    s.getId(),
                    s.getName(),
                    s.getEmail(),
                    s.getLevel());
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void printStudentLessons(LessonListDTO dto) {
        if (dto == null || dto.getLessons() == null || dto.getLessons().isEmpty()){
            System.out.println("Δεν βρέθηκαν μαθήματα για εκτύπωση.");
            return;
        }


        System.out.printf("%-20s | %-10s %n%n", dto.getName(), dto.getLevel());
        //TODO implementation for fat comments - homework -> new line

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-35s | %-35s %n", "ΗΜΕΡΟΜΗΝΙΑ", "ΑΣΚΗΣΕΙΣ", "ΣΧΟΛΙΑ");
        System.out.println("------------------------------------------------------------------------------------------------");

        for (LessonPrintDTO l : dto.getLessons()){
            System.out.printf("%-20s | %-35s | %-35s %n",
                    l.getDate(), l.getHomework(), l.getComments());
        }


    }

    public void printFullProfile (StudentProfileDTO profile){
        StudentPrintDTO student = profile.getStudentDetails();
        List<LessonPrintDTO> lessons = profile.getLessons();

        System.out.println("\n" + "=".repeat(95));
        System.out.println("                               ΠΛΗΡΗΣ ΚΑΡΤΕΛΑ ΜΑΘΗΤΗ");
        System.out.println("=".repeat(95));

        // Student
        System.out.printf(" Ονοματεπώνυμο: %-30s | ID: %-5d%n", student.getName(), student.getId());
        System.out.printf(" Email:         %-30s | Level: %-10s%n", student.getEmail(), student.getLevel());
        System.out.println("-".repeat(95));


        if (lessons.isEmpty()) {
            System.out.println(" [!] Δεν βρέθηκε ιστορικό μαθημάτων για τον συγκεκριμένο μαθητή.");
        } else {
            System.out.println(" ΙΣΤΟΡΙΚΟ ΜΑΘΗΜΑΤΩΝ:");
            System.out.printf(" %-15s | %-35s | %-35s%n", "ΗΜΕΡΟΜΗΝΙΑ", "HOMEWORK / ΑΣΚΗΣΕΙΣ", "ΣΧΟΛΙΑ ΔΑΣΚΑΛΟΥ");
            System.out.println(" " + "-".repeat(92));
            lessons.forEach(l -> {
                System.out.printf(" %-15s | %-35s | %-35s%n",
                                l.getDate(), l.getHomework(), l.getComments());
                    }
            );

        }
    }

}

package io.github.amichailides.view;

import io.github.amichailides.dto.*;


import java.util.List;


public class StudentPrinter {

    public void printFullProfile(StudentProfileDTO profile) {
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
            System.out.printf(" %-2s | %-15s | %-35s | %-35s%n","ID", "ΗΜΕΡΟΜΗΝΙΑ", "HOMEWORK / ΑΣΚΗΣΕΙΣ", "ΣΧΟΛΙΑ ΔΑΣΚΑΛΟΥ");
            System.out.println(" " + "-".repeat(92));
            int counter = 1;
            for (LessonPrintDTO l : profile.getLessons()) {
                System.out.printf(" %-2s | %-15s | %-35s | %-35s%n",
                        counter++,
                        l.getDate(),
                        l.getHomework(),
                        l.getComments());
                    }

        }
    }

    public void printStudentTable(List<StudentPrintDTO> students, String header) {

        System.out.println("\n=== " + header.toUpperCase() + " ===");

        if (students == null || students.isEmpty()) {
            System.out.println("Δεν βρέθηκαν μαθητές για εκτύπωση.");
            return;
        }

        System.out.println("----------------------------------------------------------------------------");
        System.out.printf("%-5s | %-25s | %-25s | %-10s%n", "ID", "ΟΝΟΜΑΤΕΠΩΝΥΜΟ", "EMAIL", "LEVEL");
        System.out.println("----------------------------------------------------------------------------");

        for (StudentPrintDTO s : students) {
            System.out.printf("%-5s | %-25s | %-25s | %-10s%n",
                    s.getId(),
                    s.getName(), // first + last name combined απο το DTO
                    s.getEmail(),
                    s.getLevel());
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void printSuccess(String message) {
        System.out.println("\n ΕΠΙΤΥΧΙΑ: " + message);
    }

    public void printError(String message) {
        System.out.println("\n ΣΦΑΛΜΑ: " + message);
    }

    public void printStudentUpdateHeader() {
        System.out.println("\n==========================================");
        System.out.println("       ΕΝΗΜΕΡΩΣΗ ΣΤΟΙΧΕΙΩΝ ΜΑΘΗΤΗ");
        System.out.println("==========================================");
        System.out.println("   Οδηγια: Πατηστε [ENTER] σε οποιοδηποτε");
        System.out.println("   πεδιο για να διατηρησετε την υπρχουσα");
        System.out.println("   τιμη χωρις αλλαγη.");
        System.out.println("------------------------------------------");
    }

    public void printLessonUpdateHeader() {
        System.out.println("\n==========================================");
        System.out.println("       ΕΝΗΜΕΡΩΣΗ ΣΤΟΙΧΕΙΩΝ ΜΑΘΗΜΑΤΟΣ");
        System.out.println("==========================================");
        System.out.println("   Οδηγια: Πατηστε [ENTER] σε οποιοδηποτε");
        System.out.println("   πεδιο για να διατηρησετε την υπρχουσα");
        System.out.println("   τιμη χωρις αλλαγη.");
        System.out.println("------------------------------------------");
    }

    public void printPostDisplayActions() {
        System.out.println("\n====================");
        System.out.println("1. Ενημέρωση Στοιχείων Φοιτητή");
        System.out.println("2. Update Lesson");
        System.out.println("0. Back to main menu");
        System.out.println("====================");

    }

    public void clearScreen() {
        try {
            // Δημιουργούμε μια διεργασία που εκτελεί την εντολή 'cls' των Windows
            // Το .inheritIO() συνδέει την έξοδο της εντολής με την κονσόλα της Java
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Εδώ δεν κάνουμε τίποτα - αν το σύστημα δεν υποστηρίζει cls,
            // απλά συνεχίζουμε χωρίς να "βρωμίζουμε" την οθόνη.
        }
    }

}


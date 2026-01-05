package io.github.amichailides.view;

import io.github.amichailides.dto.StudentListDTO;
import io.github.amichailides.dto.StudentPrintDTO;


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


}

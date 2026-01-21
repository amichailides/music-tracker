package io.github.amichailides;

//  IStudentRepository repository = new InMemoryRepository();
//  StudentRepository studentRepo = new SqlStudentRepository();
import io.github.amichailides.controller.Controller;
import io.github.amichailides.repository.*;
import io.github.amichailides.service.Service;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;

import java.util.Scanner;

public class AppFactory {

    public static Controller createController(Scanner scanner, StudentPrinter printer) {

        StudentRepository studentRepo = new JpaStudentRepository();
        LessonRepository lessonRepo = new JpaLessonRepository();

        Service service = new Service(studentRepo, lessonRepo);

        InputHandler inputHandler = new InputHandler(scanner, printer);


        StudentDataEntry studentEntry = new StudentDataEntry(inputHandler);
        LessonDataEntry lessonEntry = new LessonDataEntry(inputHandler);


        return Controller.builder()
                .service(service)
                .inputHandler(inputHandler)
                .printer(printer)
                .lessonEntry(lessonEntry)
                .studentEntry(studentEntry)
                .build();
    }
}
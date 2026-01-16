package io.github.amichailides;

//  IStudentRepository repository = new InMemoryRepository();
//  StudentRepository studentRepo = new SqlStudentRepository();
import io.github.amichailides.controller.Controller;
import io.github.amichailides.repository.JpaStudentRepository;
import io.github.amichailides.repository.LessonRepository;
import io.github.amichailides.repository.SqlLessonRepository;
import io.github.amichailides.repository.StudentRepository;
import io.github.amichailides.service.Service;
import io.github.amichailides.utils.InputHandler;
import io.github.amichailides.view.LessonDataEntry;
import io.github.amichailides.view.StudentDataEntry;
import io.github.amichailides.view.StudentPrinter;

import java.util.Scanner;

public class AppFactory {

    public static Controller createController(Scanner scanner) {

        StudentRepository studentRepo = new JpaStudentRepository();
        LessonRepository lessonRepo = new SqlLessonRepository();

        Service service = new Service(studentRepo, lessonRepo);

        InputHandler inputHandler = new InputHandler(scanner);
        StudentPrinter printer = new StudentPrinter();


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
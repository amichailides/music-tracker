package io.github.amichailides;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.dto.StudentListDTO;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;
import io.github.amichailides.repository.IStudentRepository;
import io.github.amichailides.repository.InMemoryRepository;
import io.github.amichailides.service.Service;
import io.github.amichailides.view.StudentPrinter;

import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        IStudentRepository repository = new InMemoryRepository();
        Service service = new Service(repository);
        StudentPrinter printer = new StudentPrinter();

        StudentCreateDTO studentDTO = StudentCreateDTO.builder()
                .firstName("Nikos")
                .lastName("Matsablokos")
                .email("Fousekis@openai.com")
                .mobile("6946729648")
                .level(SkillLevel.ADVANCED)
                .build();
        StudentCreateDTO studentDTO2 = StudentCreateDTO.builder()
                .firstName("Nikos")
                .lastName("Korobos")
                .email("Korobos@openai.com")
                .mobile("6946729648")
                .level(SkillLevel.INTERMEDIATE)
                .build();

        LessonCreateDTO lessonDTO = LessonCreateDTO.builder()
                .date(LocalDate.now())
                .comments("Wrist position, small pick movement ")
                .homework("Berklee guitar 1, page 64")
                .build();

        service.registerStudent(studentDTO);
        service.registerStudent(studentDTO2);

        // pull in memory DB from repository
        List<Student> allStudents = repository.findAll();

        // create packet for print
        StudentListDTO packageForPrinting = service.prepareStudentsForPrint(allStudents);


        printer.printAllStudents(packageForPrinting);
        service.addLesson(1L, lessonDTO);
        System.out.println(service.getStudentLessons(1L));


    }
}
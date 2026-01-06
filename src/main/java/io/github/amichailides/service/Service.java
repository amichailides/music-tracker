package io.github.amichailides.service;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.Lesson;
import io.github.amichailides.model.Student;
import io.github.amichailides.repository.IStudentRepository;

import java.util.*;

public class Service {
    private final IStudentRepository repository;

    public Service(IStudentRepository repository) {
        this.repository = repository;
    }

    public Student registerStudent(StudentCreateDTO dto){
        Student student = Student.createFromDTO(dto);
        return repository.save(student);
    }


    public Lesson addLesson(Long id, LessonCreateDTO dto){
        Objects.requireNonNull(id, "ID can't be null");
        Objects.requireNonNull(dto, "DTO can't be null");

        Student student = repository.findById(id).orElseThrow();
        Lesson lesson = Lesson.createFromDTO(dto);
        student.getLessons().add(lesson);
        repository.save(student);

        return lesson;
    }

    public List<Lesson> getStudentLessons(Long studentId) {
        Objects.requireNonNull(studentId, "Id can't be null");
        Student student = repository.findById(studentId).orElseThrow();
        return new ArrayList<>(student.getLessons());
    }

    public StudentListDTO prepareStudentsForPrint(List<Student> students) {
        Objects.requireNonNull(students, "Student's can't be null");

        List<StudentPrintDTO> printList = students.stream()
                .map(s -> StudentPrintDTO.from(s) )
                .toList();

       return new StudentListDTO(printList);
    }

    public LessonListDTO prepareLessonsForPrint(Long id) {
        Objects.requireNonNull(id, "Id can't be null");

        Optional<Student> student = repository.findById(id);
        Student s = student.orElseThrow(
                () -> new IllegalArgumentException("δεν υπαρχει χρηστης με id: " + id)
        );

        List<LessonPrintDTO> lessonsPrintList = s.getLessons().stream()
                .map(l -> LessonPrintDTO.fromEntity(l))
                .toList();

        return new LessonListDTO(lessonsPrintList, s.getFirstName() + " " + s.getLastName(), s.getLevel().toString());
    }

}

package io.github.amichailides.service;

import io.github.amichailides.dto.LessonCreateDTO;
import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.model.Lesson;
import io.github.amichailides.model.Student;
import io.github.amichailides.repository.IStudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}

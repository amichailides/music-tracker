package io.github.amichailides.repository;

import io.github.amichailides.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    Student save(Student student);
    List<Student> findByLastName (String Lastname);
    Optional<Student> findById (Long id);
}

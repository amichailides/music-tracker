package io.github.amichailides.repository;

import io.github.amichailides.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    List<Student> findByLastName (String Lastname);
    Optional<Student> findById (Long studentId);
    List<Student> findAll();
    boolean deleteById(Long studentId);
    boolean existsById(Long studentId);
}

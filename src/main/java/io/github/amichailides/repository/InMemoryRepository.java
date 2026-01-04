package io.github.amichailides.repository;

import io.github.amichailides.model.Student;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryRepository implements IStudentRepository{
    private HashMap<Long, Student> database = new HashMap<>();
    private static Long id = 1L;

    public Student save(Student student){
        if (student == null) {
            throw new IllegalArgumentException("Student can't be null!");
        }
        if (student.getId() == null) {
            student.setId(id);
            id++;
        }

        database.put(student.getId(), student);
        return student;
    }

    @Override
    public List<Student> findByLastName (String lastName) {
        return database.values().stream()
                .filter(s -> s.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    @Override
    public Optional<Student> findById(Long id){
        return Optional.ofNullable(database.get(id));
    }
}

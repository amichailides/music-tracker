package io.github.amichailides.repository;

import io.github.amichailides.model.Student;

import java.util.*;

public class InMemoryRepository implements StudentRepository {
    private final HashMap<Long, Student> database = new HashMap<>();
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

    @Override
    public List<Student> findAll() {
        return List.copyOf(database.values());
        //return new ArrayList<>(database.values());
    }

    @Override
    public void delete(Student student) {
        database.remove(student.getId());
    }

    @Override
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }

    public Optional<Student> findByIdWithLessons(Long id) {
        //TODO
        return Optional.empty();
    }
}

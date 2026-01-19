package io.github.amichailides.repository;

import io.github.amichailides.model.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    Lesson save(Lesson lesson);
    List<Lesson> findByStudentId(Long studentId);
    boolean deleteById (Long studentId);
    Optional<Lesson> findById(Long lessonId);
}

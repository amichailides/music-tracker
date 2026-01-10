package io.github.amichailides.repository;

import io.github.amichailides.model.Lesson;

import java.util.List;

public interface LessonRepository {
    Lesson save(Lesson lesson, Long id);
    List<Lesson> findById(Long id);
}

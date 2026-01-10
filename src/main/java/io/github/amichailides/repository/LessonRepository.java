package io.github.amichailides.repository;

import io.github.amichailides.model.Lesson;

public interface LessonRepository {
    Lesson save(Lesson lesson, Long id);
}

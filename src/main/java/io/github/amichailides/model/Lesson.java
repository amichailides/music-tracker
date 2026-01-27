package io.github.amichailides.model;

import io.github.amichailides.dto.LessonCreateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "lesson_date")
    private LocalDate date;

    @Column(name = "lesson_comments")
    private String comments;

    @Column(name = "homework")
    private String homework;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson that)) return false;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    public static Lesson createFromDTO(LessonCreateDTO dto) {
        return Lesson.builder()
                .date(dto.getDate())
                .comments(dto.getComments())
                .homework(dto.getHomework())
                .build();
    }
}

package io.github.amichailides.model;

import io.github.amichailides.dto.LessonCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String comments;
    private String homework;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public static Lesson createFromDTO(LessonCreateDTO dto) {
        return Lesson.builder()
                .date(dto.getDate())
                .comments(dto.getComments())
                .homework(dto.getHomework())
                .build();
    }
}

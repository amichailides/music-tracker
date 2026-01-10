package io.github.amichailides.model;

import io.github.amichailides.dto.LessonCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    private Long id;
    private LocalDate date;
    private String comments;
    private String homework;

    public static Lesson createFromDTO(LessonCreateDTO dto) {
        return Lesson.builder()
                .date(dto.getDate())
                .comments(dto.getComments())
                .homework(dto.getHomework())
                .build();
    }
}

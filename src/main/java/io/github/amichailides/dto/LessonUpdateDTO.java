package io.github.amichailides.dto;

import io.github.amichailides.model.Lesson;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class LessonUpdateDTO {
    private String date;
    private String comments;
    private String homework;


    public static LessonUpdateDTO fromEntity(Lesson lesson){
        return LessonUpdateDTO.builder()
                .date(lesson.getDate().toString())
                .comments(lesson.getComments())
                .homework(lesson.getHomework())
                .build();
    }
}



package io.github.amichailides.dto;

import io.github.amichailides.model.Lesson;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LessonPrintDTO {
     String date;
     String comments;
     String homework;

    public static LessonPrintDTO fromEntity(Lesson lesson){
        return LessonPrintDTO.builder()
                .date(lesson.getDate().toString())
                .comments(lesson.getComments())
                .homework(lesson.getHomework())
                .build();
    }
}

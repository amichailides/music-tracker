package io.github.amichailides.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class LessonUpdateDTO {
    private LocalDate date;
    private String comments;
    private String homework;
}

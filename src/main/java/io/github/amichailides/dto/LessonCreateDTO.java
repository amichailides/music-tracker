package io.github.amichailides.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class LessonCreateDTO {
    LocalDate date;
    String comments;
    String homework;
}

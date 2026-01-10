package io.github.amichailides.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;
@Value
@Builder
@AllArgsConstructor
public class StudentProfileDTO {
    StudentPrintDTO studentDetails;
    List<LessonPrintDTO> lessons;

}

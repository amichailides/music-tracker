package io.github.amichailides.dto;

import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentPrintDTO {
     Long id;
     String email;
     String name;
     SkillLevel level;

     public static StudentPrintDTO from (Student student){
         return StudentPrintDTO.builder()
                 .id(student.getId())
                 .name(student.getFirstName() + " " + student.getLastName())
                 .email(student.getEmail())
                 .level(student.getLevel())
                 .build();
     }
}

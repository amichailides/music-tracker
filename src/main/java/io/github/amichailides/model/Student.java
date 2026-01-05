package io.github.amichailides.model;

import io.github.amichailides.dto.StudentCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private SkillLevel level;

    @Builder.Default
    private List<Lesson> lessons = new ArrayList<>();

    public static Student createFromDTO(StudentCreateDTO dto) {
        //creates empty lessons list because @Builder.Default
        return Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .level(dto.getLevel())
                .build();

    }

}

package io.github.amichailides.dto;

import io.github.amichailides.model.SkillLevel;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentCreateDTO {
    String firstName;
    String lastName;
    String email;
    String mobile;
    SkillLevel level;
}
package io.github.amichailides.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String level;
}

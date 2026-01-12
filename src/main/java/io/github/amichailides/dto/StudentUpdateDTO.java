package io.github.amichailides.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    // TODO: Προσθήκη πεδίου mobile στο DTO και στον Builder για την ενημέρωση στοιχείων
    private String level;
}

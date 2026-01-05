package io.github.amichailides.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentListDTO {
    // ΜΟΝΟ ΑΥΤΟ: Μια λίστα από τα "μικρά" DTOs που έφτιαξες
    private List<StudentPrintDTO> students;
}
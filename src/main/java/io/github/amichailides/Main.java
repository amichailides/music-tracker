package io.github.amichailides;

import io.github.amichailides.dto.StudentCreateDTO;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.repository.IStudentRepository;
import io.github.amichailides.repository.InMemoryRepository;
import io.github.amichailides.service.Service;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        IStudentRepository repository = new InMemoryRepository();
        Service service = new Service(repository);

        StudentCreateDTO createDTO = StudentCreateDTO.builder()
                .firstName("Nikos")
                .lastName("Matsablokos")
                .email("Fousekis@openai.com")
                .mobile("6946729648")
                .level(SkillLevel.ADVANCED)
                .build();

    }
}
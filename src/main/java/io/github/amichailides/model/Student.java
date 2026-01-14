package io.github.amichailides.model;

import io.github.amichailides.dto.StudentCreateDTO;
import jakarta.persistence.*;
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
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;
    @Column(name = "skill_level")
    @Enumerated(EnumType.STRING)
    private SkillLevel level;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true )
    @Builder.Default // if no insert -> builder inserts new ArrayList<>(); -> no NullPointException
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

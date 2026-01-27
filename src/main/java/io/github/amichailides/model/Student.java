package io.github.amichailides.model;

import io.github.amichailides.dto.StudentCreateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

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
    private Set<Lesson> lessons = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student that)) return false;
        return Objects.equals(uuid,that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setStudent(this);
    }

    public void removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setStudent(null);
    }

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

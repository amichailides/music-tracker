package io.github.amichailides.service;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.Lesson;
import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;
import io.github.amichailides.repository.LessonRepository;
import io.github.amichailides.repository.StudentRepository;

import java.time.LocalDate;
import java.util.*;

public class Service {
    private final StudentRepository studentRepo;
    private final LessonRepository lessonRepo;
    // TODO: Χρησιμοποίησε την απλή findById για ανάγνωση βασικών στοιχείων (ταχύτητα).
    // TODO: Χρησιμοποίησε την findByIdWithLessons όταν χρειάζεται πρόσβαση ή τροποποίηση στα Lessons (αποφυγή LazyInitializationException).
    public Service(StudentRepository studentRepo, LessonRepository lessonRepo) {
        this.studentRepo = studentRepo;
        this.lessonRepo = lessonRepo;
    }

    public Student registerStudent(StudentCreateDTO dto){
        Student student = Student.createFromDTO(dto);
        return studentRepo.save(student);
    }

    public Lesson addLesson(Long studentId, LessonCreateDTO dto){
        Objects.requireNonNull(studentId, "ID can't be null");
        Objects.requireNonNull(dto, "DTO can't be null");

        Student student = studentRepo.findByIdWithLessons(studentId)
                .orElseThrow(() -> new RuntimeException("Δεν βρεθηκε μαθητης με το ID: " + studentId));

        Lesson lesson = Lesson.createFromDTO(dto);
        student.addLesson(lesson);

        return lessonRepo.save(lesson);
    }

    public StudentListDTO prepareStudentsForPrint() {

        List<Student> students = studentRepo.findAll();
        List<StudentPrintDTO> printList = students.stream()
                .map(s -> StudentPrintDTO.from(s) )
                .toList();

       return new StudentListDTO(printList);
    }

    public void deleteStudent(Long id) {
        Objects.requireNonNull(id, "id can't be null");
        // CascadeType.ALL διαγραφει τα lesson  οποτε "plain findById()
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Δεν βρεθηκε μαθητης με το ID: " + id));

        studentRepo.delete(student);

    }

    public Optional<StudentProfileDTO> getStudentProfile(Long studentId) {
        Objects.requireNonNull(studentId, "studentId can't be null");

        return studentRepo.findByIdWithLessons(studentId).map(student -> StudentProfileDTO.builder()
                .studentDetails(StudentPrintDTO.from(student))
                .lessons(student.getLessons().stream()
                        .map(LessonPrintDTO::fromEntity)
                        .toList())
                .build());
    }

    public StudentUpdateDTO getStudentForUpdate(Long studentId) {
        Student s = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return StudentUpdateDTO.builder()
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .mobile(s.getMobile())
                .level(s.getLevel().name())
                .build();
    }

    public LessonUpdateDTO getLessonForUpdate(Long lessonId) {
        return lessonRepo.findById(lessonId)
                .map(lesson -> LessonUpdateDTO.fromEntity(lesson)) // Μετατροπή σε DTO
                .orElseThrow(() -> new RuntimeException("Lesson with id " + lessonId + " not found"));
    }

    public Long updateStudent(Long id, StudentUpdateDTO changed) {
        Objects.requireNonNull(id, "Id can't be null");
        Objects.requireNonNull(changed, "New data can't be null");

        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Δεν βρεθηκε μαθητης με id: " + id));
        if (changed.getFirstName() != null && !changed.getFirstName().isBlank()) {
            s.setFirstName(changed.getFirstName());
        }

        if (changed.getLastName() != null && !changed.getLastName().isBlank()) {
            s.setLastName(changed.getLastName());
        }

        if (changed.getEmail() != null && !changed.getEmail().isBlank()) {
            s.setEmail(changed.getEmail());
        }

        if (changed.getMobile() != null && !changed.getMobile().isBlank()) {
            s.setMobile(changed.getMobile());
        }
        if (changed.getLevel() != null && !changed.getLevel().isBlank()) {
            s.setLevel(SkillLevel.valueOf(changed.getLevel()));
        }
        Student updatedStudent = studentRepo.save(s);

        return updatedStudent.getId();

    }

    public Long updateLesson(Long lessonId, LessonUpdateDTO changed) {
        Lesson lesson  = lessonRepo.findById(lessonId)
                .orElseThrow( ()-> new RuntimeException("Δεν βρεθηκε μαθημα με ID: " + lessonId));

        if (changed.getDate() != null && !changed.getDate().isBlank()) {
            lesson.setDate(LocalDate.parse(changed.getDate()));
        }

        if (changed.getComments() !=null && !changed.getComments().isBlank()) {
            lesson.setComments(changed.getComments());
        }

        if (changed.getHomework() != null && !changed.getHomework().isBlank()) {
            lesson.setHomework(changed.getHomework());
        }

        Lesson updatedLesson = lessonRepo.save(lesson);
        return updatedLesson.getId();
    }

    public StudentListDTO findStudentsByLastName(String lastName){

        List<Student> matchingStudents = studentRepo.findByLastName(lastName);

        List<StudentPrintDTO> printDTOS =  matchingStudents.stream()
                .map(StudentPrintDTO::from)
                .toList();

        return new StudentListDTO(printDTOS);
    }

    public void deleteLesson(Long studentId, Long lessonId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Ο μαθητης με ID: [" + studentId + "] δεν βρεθηκε"));

        Lesson lesson = student.getLessons().stream()
                .filter(l -> l.getId().equals(lessonId))
                .findFirst()
                .orElseThrow( () -> new RuntimeException("Το μαθημα δεν βρεθηκε"));

        student.removeLesson(lesson);

        lessonRepo.delete(lesson);

    }

}

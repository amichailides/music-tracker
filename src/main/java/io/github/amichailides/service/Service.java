package io.github.amichailides.service;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.Lesson;
import io.github.amichailides.model.Student;
import io.github.amichailides.repository.LessonRepository;
import io.github.amichailides.repository.StudentRepository;

import java.util.*;

public class Service {
    private final StudentRepository studentRepo;
    private final LessonRepository lessonRepo;

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

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Lesson lesson = Lesson.createFromDTO(dto);

        return lessonRepo.save(lesson, student.getId());
    }

    public StudentListDTO prepareStudentsForPrint(List<Student> students) {
        Objects.requireNonNull(students, "Student's can't be null");

        List<StudentPrintDTO> printList = students.stream()
                .map(s -> StudentPrintDTO.from(s) )
                .toList();

       return new StudentListDTO(printList);
    }

    public LessonListDTO prepareLessonsForPrint(Long studentId) {
        Objects.requireNonNull(studentId, "Id can't be null");

        Student student = studentRepo.findById(studentId)
                .orElseThrow( () -> new IllegalArgumentException("Δεν υπαρχει μαθητης με id: " + studentId));

        List<Lesson> lessons = lessonRepo.findById(studentId);

        List<LessonPrintDTO> lessonsPrintList = lessons.stream()
                .map(l -> LessonPrintDTO.fromEntity(l))
                .toList();

        return new LessonListDTO(
                lessonsPrintList,
                student.getFirstName() + " " + student.getLastName(),
                student.getLevel().toString());
    }

    public void deleteStudent(Long id) {
        Objects.requireNonNull(id, "id can't be null");

        if (!studentRepo.existsById(id)) {
            throw new NoSuchElementException("Ο μαθητής δεν βρέθηκε.");
        }
        studentRepo.deleteById(id);
    }

    public List<Lesson> getStudentHistory(Long studentId){
        Objects.requireNonNull(studentId, "Student id can't be null");

        studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        // TODO: Μελλοντικά, μετάτρεψε τη List<Lesson> (Model) σε List<LessonResponseDTO>
        // για να μην εκθέτουμε το Database Entity απευθείας στο UI/API.
        return lessonRepo.findById(studentId);
    }

    public Optional<StudentProfileDTO> getStudentProfile(Long studentId){
        Objects.requireNonNull(studentId, "studentId can't be null");
        //pull all lessons, create student lesson dto -> StudentProfileDTO,
        return studentRepo.findById(studentId).map(student -> {
            List<Lesson> lessons = lessonRepo.findById(studentId);

            return StudentProfileDTO.builder()
                    .studentDetails(StudentPrintDTO.from(student))
                    .lessons(lessons.stream().map(l -> LessonPrintDTO.fromEntity(l)).toList())
                    .build();
        });
    }


}

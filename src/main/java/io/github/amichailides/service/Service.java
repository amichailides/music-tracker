package io.github.amichailides.service;

import io.github.amichailides.dto.*;
import io.github.amichailides.model.Lesson;
import io.github.amichailides.model.SkillLevel;
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
        lesson.setStudent(student);

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

        boolean deleted = studentRepo.deleteById(id);

        if (!deleted) {
            throw new NoSuchElementException("Ο μαθητής δεν βρέθηκε.");
        }
    }

    public Optional<StudentProfileDTO> getStudentProfile(Long studentId){
        Objects.requireNonNull(studentId, "studentId can't be null");
        //pull all lessons, create student lesson dto -> StudentProfileDTO,
        return studentRepo.findById(studentId).map(student -> {
            List<Lesson> lessons = lessonRepo.findByStudentId(studentId);

            return StudentProfileDTO.builder()
                    .studentDetails(StudentPrintDTO.from(student))
                    .lessons(lessons.stream().map(l -> LessonPrintDTO.fromEntity(l)).toList())
                    .build();
        });
    }

    public StudentUpdateDTO getStudentForUpdate(Long id) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return StudentUpdateDTO.builder()
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .mobile(s.getMobile())
                .level(s.getLevel().name())
                .build();
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

    public StudentListDTO findStudentsByLastName(String lastName){

        List<Student> matchingStudents = studentRepo.findByLastName(lastName);

        List<StudentPrintDTO> printDTOS =  matchingStudents.stream()
                .map(StudentPrintDTO::from)
                .toList();

        return new StudentListDTO(printDTOS);
    }

}
